package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.runnable.AbstractWorker;
import javafx.application.Platform;

import java.util.*;

public class EventManager {

    private static final Queue<Event> eventsToProcess = new LinkedList<>();
    private static final Worker worker = new Worker(eventsToProcess);
    private static Map<Class<? extends Event>, List<EventListener<? extends Event>>> map = new HashMap<>();

    static {
        new Thread(worker, "EventWorker").start();
    }

    public static <T extends Event> void addListener(Class<T> eventClass, EventListener<T> listener) {
        if (!map.containsKey(eventClass)) {
            map.put(eventClass, new LinkedList<>());
        }
        map.get(eventClass).add(listener);
    }

    public static <T extends Event> void removeListener(Class<T> eventClass,
                                                        EventListener<T> listener) {
        if (!map.containsKey(eventClass)) {
            return;
        }
        map.get(eventClass).remove(listener);
    }

    /**
     * Adds a new event to event queue. The event will be handed over to listeners at some point in the future.
     * Events will be handled sequentially meaning a event added using fireEvent will be process before any event from
     * subsequent fireEvent calls will be handled.
     * <p>
     * In case of GUI adaptions a listener call will be forwarded to JavaFX event queue using
     * {@link Platform#runLater(Runnable)}. This
     * can cause and additional wait time for an event to be fully processed.
     */
    public static <T extends Event> void fireEvent(T event) {
        synchronized (eventsToProcess) {
            eventsToProcess.add(event);
            eventsToProcess.notifyAll();
        }
    }

    public static void stopWorker() {
        worker.invokeStop();
    }

    private static class Worker extends AbstractWorker<Event> {
        Worker(Queue<Event> taskQueue) {
            super(taskQueue);
        }

        protected <T extends Event> void handleTask(T event) {
            List<EventListener<?>> listeners = map.get(event.getClass());
            if (listeners != null) {
                listeners.forEach(listener -> {
                    if (listener.runOnJavaFxThread()) {
                        Platform.runLater(() -> {
                            //noinspection unchecked
                            ((EventListener<T>) listener).handleEvent(event);
                        });
                    } else {
                        //noinspection unchecked
                        ((EventListener<T>) listener).handleEvent(event);
                    }
                });
            }
        }
    }
}
