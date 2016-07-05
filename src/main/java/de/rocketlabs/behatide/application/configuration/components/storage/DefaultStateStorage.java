package de.rocketlabs.behatide.application.configuration.components.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rocketlabs.behatide.application.configuration.components.exceptions.StateStorageException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultStateStorage implements StateStorage {

    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private static final String STORAGE_DIRECTORY = USER_DIRECTORY + File.separator + ".behatIde";

    private Gson gson = new GsonBuilder().create();

    @Override
    public <T> T getState(@NotNull Class<T> stateClass) {
        State state = stateClass.getAnnotation(State.class);
        if (state == null) {
            throw new StateStorageException("Given class is not annotated with @State");
        }

        try (FileReader reader = new FileReader(getFilePath(state))) {
            return gson.fromJson(reader, stateClass);
        } catch (IOException e) {
            throw new StateStorageException("Could not load save file", e);
        }
    }

    @Override
    public boolean saveState(@NotNull Object data, State state) {
        String filePath = getFilePath(state);
        ensureStorageDirectory();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new StateStorageException("Could not write save file", e);
        }
        return true;
    }

    @Override
    public boolean hasState(@NotNull State state) {
        File file = new File(getFilePath(state));
        return file.exists() && file.isFile();
    }

    @NotNull
    private String getFilePath(State state) {
        return getStorageDirectory() + File.separator + state.name() + ".json";
    }

    private void ensureStorageDirectory() {
        File dir = new File(getStorageDirectory());
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
    }

    @NotNull
    protected String getStorageDirectory() {
        return STORAGE_DIRECTORY;
    }
}
