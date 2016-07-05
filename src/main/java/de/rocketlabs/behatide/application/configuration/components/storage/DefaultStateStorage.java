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

    @Override
    public <T> T getState(@NotNull Class<T> stateClass, Storage storage) {
        File file = new File(getFilePath(storage));

        try (FileReader reader = new FileReader(file)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            return gson.fromJson(reader, stateClass);
        } catch (IOException e) {
            throw new StateStorageException("Could not load save file", e);
        }
    }

    @Override
    public boolean saveState(@NotNull Object data, Storage storage) {
        String filePath = getFilePath(storage);
        ensureStorageDirectory();

        try (FileWriter writer = new FileWriter(filePath)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new StateStorageException("Could not write save file", e);
        }
        return true;
    }

    @Override
    public boolean hasState(@NotNull Storage storage) {
        File file = new File(getFilePath(storage));
        return file.exists() && file.isFile();
    }

    @NotNull
    private String getFilePath(Storage storage) {
        return getStorageDirectory() + File.separator + storage.value();
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
