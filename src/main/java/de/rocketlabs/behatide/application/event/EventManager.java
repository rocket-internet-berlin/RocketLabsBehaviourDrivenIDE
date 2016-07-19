package de.rocketlabs.behatide.application.event;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventManager {

    private static Map<Class<? extends Event>, List<EventListener<? extends Event>>> map = new HashMap<>();

    public static <T extends Event> void addListener(Class<T> eventClass, EventListener<T> listener) {
        if (!map.containsKey(eventClass)) {
            map.put(eventClass, new LinkedList<>());
        }
        map.get(eventClass).add(listener);
    }

    public static <T extends Event> void fireEvent(T event) {
        List<EventListener<?>> listeners = map.get(event.getClass());
        if (listeners != null) {
            //noinspection unchecked
            listeners.forEach(listener -> ((EventListener<T>) listener).handleEvent(event));
        }
    }
}
