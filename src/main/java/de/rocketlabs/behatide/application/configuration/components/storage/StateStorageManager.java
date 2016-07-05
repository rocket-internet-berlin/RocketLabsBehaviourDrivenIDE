package de.rocketlabs.behatide.application.configuration.components.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private Map<State, String> statesToSave = new HashMap<>();
    private Gson gson = new GsonBuilder().create();


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
            if (stateStorage.hasState(state)) {
                return deserializeData(stateClass, stateStorage.loadData(state));
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

        statesToSave.put(state, serializeData(data));
    }

    private String serializeData(Object data) {
        return gson.toJson(data);
    }

    private <T> T deserializeData(Class<T> dataClass, String json) {
        return gson.fromJson(json, dataClass);
    }

    public void save() {
        statesToSave.keySet().parallelStream().forEach(state -> {
            String data = statesToSave.get(state);
            for (Storage storage : state.storages()) {
                StateStorage stateStorage = getStateStorage(storage.storageClass());
                if (stateStorage.saveState(data, state)) {
                    break;
                }
            }
        });
    }

}
