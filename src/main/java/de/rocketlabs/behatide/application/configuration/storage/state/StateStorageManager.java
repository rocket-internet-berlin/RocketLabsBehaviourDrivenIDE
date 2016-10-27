package de.rocketlabs.behatide.application.configuration.storage.state;

import de.rocketlabs.behatide.application.configuration.exceptions.StateStorageException;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StateStorageManager {

    private static StateStorageManager instance;
    private Map<Class<? extends StateStorage>, StateStorage> storageInstances = new HashMap<>();

    private Map<State, Object> loadedStates = new HashMap<>();

    private StateStorageManager() {
    }

    public static StateStorageManager getInstance() {
        if (instance == null) {
            instance = new StateStorageManager();
        }
        return instance;
    }

    @NotNull
    public <T> T loadState(@NotNull Class<T> stateClass) {
        return loadState(stateClass, new HashMap<>());
    }

    @NotNull
    public <T> T loadState(@NotNull Class<T> stateClass, Map<StorageParameter, String> parameters) {
        State state = stateClass.getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given object misses @State annotation");
        }
        if (loadedStates.containsKey(state)) {
            //noinspection unchecked
            return (T) loadedStates.get(state);
        }

        T object = null;
        for (Storage storage : state.storages()) {
            StateStorage stateStorage = getStateStorage(storage.storageClass());
            if (stateStorage.hasState(state, parameters)) {
                object = stateStorage.loadData(state, stateClass, parameters);
                break;
            }
        }
        if (object == null) {
            try {
                object = stateClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new StateStorageException("State object could not be instantiated", e);
            }
        }
        loadedStates.put(state, object);
        return object;
    }

    public <T extends StateStorage> StateStorage getStateStorage(Class<T> storageClass) throws StateStorageException {
        if (!storageClass.equals(StateStorage.class)) {
            if (!storageInstances.containsKey(storageClass)) {
                try {
                    T storage = storageClass.newInstance();
                    storageInstances.put(storageClass, storage);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new StateStorageException("Storage could not be instantiated", e);
                }
            }
            return storageInstances.get(storageClass);
        }

        return getStateStorage(JsonFileStateStorage.class);
    }

    public void save() {
        storageInstances.values().forEach(StateStorage::save);
    }

    public void setState(Object data) {
        setState(data, new HashMap<>());
    }

    public void setState(Object data, Map<StorageParameter, String> parameters) {
        State state = data.getClass().getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given object misses @State annotation");
        }

        for (Storage storage : state.storages()) {
            StateStorage stateStorage = getStateStorage(storage.storageClass());
            if (stateStorage.supports(data, parameters)) {
                stateStorage.setState(data, parameters);
            }
        }
    }
}
