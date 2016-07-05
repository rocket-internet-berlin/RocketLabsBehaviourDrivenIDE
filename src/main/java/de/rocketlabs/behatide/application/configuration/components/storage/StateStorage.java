package de.rocketlabs.behatide.application.configuration.components.storage;

import org.jetbrains.annotations.NotNull;

public interface StateStorage {

    <T> T getState(@NotNull Class<T> stateClass);

    boolean hasState(@NotNull State storage);

    boolean saveState(@NotNull Object data, State state);
}
