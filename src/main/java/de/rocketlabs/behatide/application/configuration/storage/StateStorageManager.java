package de.rocketlabs.behatide.application.configuration.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import de.rocketlabs.behatide.application.configuration.exceptions.StateStorageException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StateStorageManager {

    private static StateStorageManager instance;
    private Map<Class<? extends StateStorage>, StateStorage> storageInstances = new HashMap<>();

    private Map<State, Object> loadedStates = new HashMap<>();
    private Map<State, String> statesToSave = new HashMap<>();
    private Gson gson;

    private StateStorageManager() {
    }

    public static StateStorageManager getInstance() {
        if (instance == null) {
            instance = new StateStorageManager();
        }
        return instance;
    }

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            if (getClass().getPackage().getImplementationTitle() == null) {
                builder.setPrettyPrinting();
            }
            builder.registerTypeAdapterFactory(
                new TypeAdapterFactory() {
                    @Override
                    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

                        return null;
                    }
                }

                                              );
            gson = builder.create();
        }
        return gson;
    }

    @NotNull
    public <T> T loadState(@NotNull Class<T> stateClass) {
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
            if (stateStorage.hasState(state)) {
                object = deserializeData(stateClass, stateStorage.loadData(state));
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
        return GsonUtils.getGson().toJson(data);
    }

    private <T> T deserializeData(Class<T> dataClass, String json) {
        return GsonUtils.getGson().fromJson(json, dataClass);
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
