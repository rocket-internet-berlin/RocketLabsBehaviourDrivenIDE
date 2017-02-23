package de.rocketlabs.behatide.application.runnable;

import java.util.Queue;

/**
 * @author Jakob Erdmann
 * @since 09.02.17
 */
public abstract class AbstractWorker<T> implements Runnable {
    private final Queue<T> taskQueue;
    private boolean stopInvoked;

    public AbstractWorker(Queue<T> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (!stopInvoked) {
            T task;
            synchronized (taskQueue) {
                while (!stopInvoked && taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                task = taskQueue.poll();
            }
            if (task != null) {
                handleTask(task);
            }
        }
    }

    protected abstract <S extends T> void handleTask(S task);

    public void invokeStop() {
        stopInvoked = true;
        synchronized (taskQueue) {
            taskQueue.notifyAll();
        }
    }
}
