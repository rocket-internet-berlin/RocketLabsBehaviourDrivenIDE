package de.rocketlabs.behatide.application.configuration.components.storage;

import org.jetbrains.annotations.NotNull;

public interface StateStorage {

    <T> T getState(@NotNull Class<T> stateClass, Storage storage);

    boolean hasState(@NotNull Storage storage);

    boolean saveState(@NotNull Object state, Storage storage);
}
