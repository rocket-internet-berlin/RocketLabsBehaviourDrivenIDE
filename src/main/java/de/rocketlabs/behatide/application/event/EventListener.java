package de.rocketlabs.behatide.application.event;

public interface EventListener<T extends Event> {

    void handleEvent(T event);

    default boolean runOnJavaFxThread() {
        return false;
    }
}
