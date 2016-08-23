package de.rocketlabs.behatide.application.configuration.storage.state;

import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface StateStorage {

    <T> T loadData(@NotNull State state, Class<T> stateClass, Map<StorageParameter, String> parameters);

    boolean hasState(@NotNull State storage, Map<StorageParameter, String> parameters);

    <T extends Map<StorageParameter, String>> void setState(@NotNull Object data, T parameters);

    void save();

    boolean supports(Object data, Map<StorageParameter, String> parameters);
}
