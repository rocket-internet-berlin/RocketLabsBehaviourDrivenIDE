package de.rocketlabs.behatide.configuration.components.storage;

import de.rocketlabs.behatide.application.configuration.exceptions.StateStorageException;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class StorageManagerTest {

    private StateStorageManager storageManager;

    @Before
    public void setUp() {
        storageManager = StateStorageManager.getInstance();
    }

    @Test
    public void testGetStateStorage() {
        StateStorage stateStorage = storageManager.getStateStorage(StateStorage.class);
        Assert.assertTrue(stateStorage instanceof JsonFileStateStorage);

        stateStorage = storageManager.getStateStorage(TestStorage.class);
        Assert.assertTrue(stateStorage instanceof TestStorage);
    }

    @Test(expected = StateStorageException.class)
    public void testGetStateStoragePrivateConstructor() {
        storageManager.getStateStorage(TestStorage2.class);
    }

    @Test
    public void testLoadState() {
        TestState testState = storageManager.loadState(TestState.class);
        Assert.assertNotNull(testState);
    }

    @Test(expected = StateStorageException.class)
    public void testLoadNonState() {
        storageManager.loadState(TestState2.class);
    }

    @State(
            name = "TestState",
            storages = @Storage(
                    storageClass = TestStorage.class
            )
    )
    private static class TestState {

    }

    private static class TestState2 {

    }

    @SuppressWarnings("WeakerAccess")
    public static class TestStorage implements StateStorage {

        @Override
        public <T> T loadData(@NotNull State state, Class<T> stateClass, Map<StorageParameter, String> parameters) {

            if (state.name().equals("TestState")) {
                return (T) new TestState();
            }
            return null;
        }

        @Override
        public boolean hasState(@NotNull State storage, Map<StorageParameter, String> parameters) {
            return true;
        }

        @Override
        public <T extends Map<StorageParameter, String>> void setState(@NotNull Object data, T parameters) {
        }

        @Override
        public void save() {
        }

        @Override
        public boolean supports(Object data, Map<StorageParameter, String> parameters) {
            return true;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TestStorage2 implements StateStorage {

        public TestStorage2(String someString) {
        }

        @Override
        public <T> T loadData(@NotNull State state, Class<T> stateClass, Map<StorageParameter, String> parameters) {
            return null;
        }

        @Override
        public boolean hasState(@NotNull State storage, Map<StorageParameter, String> parameters) {
            return false;
        }

        @Override
        public <T extends Map<StorageParameter, String>> void setState(@NotNull Object data, T parameters) {
        }

        @Override
        public void save() {
        }

        @Override
        public boolean supports(Object data, Map<StorageParameter, String> parameters) {
            return true;
        }
    }

}
