package de.rocketlabs.behatide.application.action;

import de.rocketlabs.behatide.application.runnable.AbstractWorker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Jakob Erdmann
 * @since 08.02.17
 */
public class ActionRunner {
    private final static Queue<Action> actionsToProcess = new LinkedList<>();
    private final static List<Worker> workers = new ArrayList<>();

    static {
        for (int i = 1; i < 2; i++) {
            Worker worker = new Worker(actionsToProcess);
            new Thread(worker, "ActionWorker" + i).start();
            workers.add(worker);
        }
    }

    public static <T extends Action> void run(T action) {
        synchronized (actionsToProcess) {
            actionsToProcess.add(action);
            actionsToProcess.notifyAll();
        }
    }

    public static void stop() {
        workers.forEach(Worker::invokeStop);
    }

    private static class Worker extends AbstractWorker<Action> {
        Worker(Queue<Action> taskQueue) {
            super(taskQueue);
        }

        @Override
        protected <T extends Action> void handleTask(T task) {
            task.doAction();
        }
    }
}
