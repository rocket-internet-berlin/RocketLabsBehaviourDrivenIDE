package de.rocketlabs.behatide.application.configuration.components.storage;

import org.jetbrains.annotations.NotNull;

public interface StateStorage {

    String loadData(@NotNull State state);

    boolean hasState(@NotNull State storage);

    boolean saveState(@NotNull String data, State state);
}
