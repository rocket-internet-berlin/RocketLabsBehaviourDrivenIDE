package de.rocketlabs.behatide.application.configuration.components.storage;

import de.rocketlabs.behatide.application.configuration.components.exceptions.StateStorageException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class StateStorageManager {

    private static StateStorageManager instance;
    private Map<Class<? extends StateStorage>, StateStorage> storageInstances = new HashMap<>();

    private Map<String, Object> statesToSave = new HashMap<>();

    private StateStorageManager() {
    }

    public static StateStorageManager getInstance() {
        if (instance == null) {
            instance = new StateStorageManager();
        }
        return instance;
    }

    @Nullable
    public <T> T loadState(@NotNull Class<T> stateClass) {
        State state = stateClass.getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given object misses @State annotation");
        }
        for (Storage storage : state.storages()) {
            StateStorage stateStorage = getStateStorage(storage.storageClass());
            if (stateStorage.hasState(storage)) {
                return stateStorage.getState(stateClass, storage);
            }
        }
        return null;
    }

    public <T extends StateStorage> StateStorage getStateStorage(Class<T> storageClass) throws StateStorageException {
        if (!storageClass.equals(StateStorage.class)) {
            if (!storageInstances.containsKey(storageClass)) {
                try {
                    Constructor<T> constructor = storageClass.getConstructor();
                    constructor.setAccessible(true);
                    T storage = constructor.newInstance();
                    storageInstances.put(storageClass, storage);
                } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new StateStorageException("Storage could not be instantiated", e);
                }
            }
            return storageInstances.get(storageClass);
        }

        return new DefaultStateStorage();
    }

    public void setState(Object data) {
        State state = data.getClass().getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given object misses @State annotation");
        }

        statesToSave.put(state.name(), data);
    }

    public void save() {
        statesToSave.keySet().parallelStream().forEach(key -> {
            Object data = statesToSave.get(key);
            State state = data.getClass().getAnnotation(State.class);
            for (Storage storage : state.storages()) {
                StateStorage stateStorage = getStateStorage(storage.storageClass());
                if (stateStorage.saveState(data, storage)) {
                    return;
                }
            }
        });
    }

}
