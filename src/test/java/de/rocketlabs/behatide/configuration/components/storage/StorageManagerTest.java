package de.rocketlabs.behatide.configuration.components.storage;

import de.rocketlabs.behatide.application.configuration.components.exceptions.StateStorageException;
import de.rocketlabs.behatide.application.configuration.components.storage.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageManagerTest {

    private StateStorageManager storageManager;

    @Before
    public void setUp() {
        storageManager = StateStorageManager.getInstance();
    }

    @Test
    public void testGetStateStorage() {
        StateStorage stateStorage = storageManager.getStateStorage(StateStorage.class);
        Assert.assertTrue(stateStorage instanceof DefaultStateStorage);

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
        public String loadData(@NotNull State state) {
            if (state.name().equals("TestState")) {
                return "{}";
            }
            return null;
        }

        @Override
        public boolean hasState(@NotNull State storage) {
            return true;
        }

        @Override
        public boolean saveState(@NotNull String data, State state) {
            return false;
        }

    }

    @SuppressWarnings("WeakerAccess")
    public static class TestStorage2 implements StateStorage {

        public TestStorage2(String someString) {
        }

        @Override
        public String loadData(@NotNull State state) {
            return null;
        }

        @Override
        public boolean hasState(@NotNull State storage) {
            return false;
        }

        @Override
        public boolean saveState(@NotNull String data, State state) {
            return false;
        }
    }

}
