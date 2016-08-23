package de.rocketlabs.behatide.application.configuration.storage.state;

import de.rocketlabs.behatide.application.configuration.exceptions.StateStorageException;
import de.rocketlabs.behatide.application.configuration.storage.GsonUtils;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonFileStateStorage implements StateStorage {

    private static final String USER_DIRECTORY = System.getProperty("user.home");
    private static final String STORAGE_DIRECTORY = ".behatIde";

    private Map<State, Map<StorageParameter, String>> statesToSave = new HashMap<>();

    @Override
    public <T> T loadData(@NotNull State state, Class<T> stateClass, Map<StorageParameter, String> parameters) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(state, parameters)))) {
            reader.lines().forEach(data::append);
        } catch (IOException e) {
            throw new StateStorageException("Could not load save file", e);
        }
        return deserializeData(stateClass, data.toString());
    }

    @Override
    public void save() {
        statesToSave.forEach((state, parameters) -> {
            String filePath = getFilePath(state, parameters);
            ensureStorageDirectory(parameters);

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(parameters.get(StorageParameter.SERIALIZED_DATA));
            } catch (IOException e) {
                throw new StateStorageException("Could not write save file", e);
            }
        });
    }

    @Override
    public boolean supports(Object data, Map<StorageParameter, String> parameters) {
        return data.getClass().getAnnotation(State.class) != null;
    }

    @Override
    public <T extends Map<StorageParameter, String>> void setState(@NotNull Object data, T parameters) {
        State state = data.getClass().getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given object misses @State annotation");
        }
        parameters.put(StorageParameter.SERIALIZED_DATA, serializeData(data));
        statesToSave.put(state, parameters);
    }

    @Override
    public boolean hasState(@NotNull State state, Map<StorageParameter, String> parameters) {
        File file = new File(getFilePath(state, parameters));
        return file.exists() && file.isFile();
    }

    @NotNull
    private String getFilePath(State state, Map<StorageParameter, String> parameters) {
        return getStorageDirectory(parameters) + File.separator + state.name() + ".json";
    }

    private void ensureStorageDirectory(Map<StorageParameter, String> parameters) {
        File dir = new File(getStorageDirectory(parameters));
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
    }

    @NotNull
    protected String getStorageDirectory(Map<StorageParameter, String> parameters) {
        return getBaseStorageDirectory(parameters) + File.separator + STORAGE_DIRECTORY;
    }

    @NotNull
    private String getBaseStorageDirectory(Map<StorageParameter, String> parameters) {
        if (parameters != null && parameters.containsKey(StorageParameter.STORAGE_DIRECTORY)) {
            return parameters.get(StorageParameter.STORAGE_DIRECTORY);
        }
        return USER_DIRECTORY;
    }

    private String serializeData(Object data) {
        return GsonUtils.getGson().toJson(data);
    }

    private <T> T deserializeData(Class<T> dataClass, String json) {
        return GsonUtils.getGson().fromJson(json, dataClass);
    }
}
