package de.rocketlabs.behatide.application.configuration.storage;

import de.rocketlabs.behatide.application.configuration.exceptions.StateStorageException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class DefaultStateStorage implements StateStorage {

    private static final String USER_DIRECTORY = System.getProperty("user.home");
    private static final String STORAGE_DIRECTORY = USER_DIRECTORY + File.separator + ".behatIde";


    @Override
    public String loadData(@NotNull State state) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(state)))) {
            reader.lines().forEach(data::append);
        } catch (IOException e) {
            throw new StateStorageException("Could not load save file", e);
        }
        return data.toString();
    }

    @Override
    public boolean saveState(@NotNull String data, @NotNull State state) {
        String filePath = getFilePath(state);
        ensureStorageDirectory();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
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
