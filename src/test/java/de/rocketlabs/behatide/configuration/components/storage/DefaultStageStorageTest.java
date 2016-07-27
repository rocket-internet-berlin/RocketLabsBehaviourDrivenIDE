package de.rocketlabs.behatide.configuration.components.storage;

import de.rocketlabs.behatide.application.configuration.storage.DefaultStateStorage;
import de.rocketlabs.behatide.application.configuration.storage.State;
import de.rocketlabs.behatide.application.configuration.storage.StateStorageManager;
import de.rocketlabs.behatide.application.configuration.storage.Storage;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultStageStorageTest {

    private static final String SERIALIZED_OBJECT = "{" +
            "\"testString\":\"Test\"," +
            "\"testInt\":2843138," +
            "\"testFloat\":13843.144," +
            "\"testLong\":2834346478494576546," +
            "\"testDouble\":4.387647676767677E15," +
            "\"testList\":[\"Test\",2843138,13843.144,2834346478494576546,4.387647676767677E15]," +
            "\"testMap\":{\"2834346478494576546\":2834346478494576546,\"Test\":\"Test\"," +
            "\"4.387647676767677E15\":4.387647676767677E15,\"2843138\":2843138,\"13843.144\":13843.144}" +
            "}";
    private StateStorageManager storageManager;
    private File saveFile;

    @Before
    public void setUp() {
        storageManager = StateStorageManager.getInstance();
        saveFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "TestState.json");
    }

    @Test
    public void testSave() {
        TestState testState = new TestState();
        testState.setValues();
        storageManager.setState(testState);
        storageManager.save();

        Assert.assertTrue(saveFile.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String line = reader.readLine();
            Assert.assertEquals(SERIALIZED_OBJECT, line);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        //noinspection ResultOfMethodCallIgnored
        saveFile.delete();
    }

    @Test
    public void testLoad() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(SERIALIZED_OBJECT);
            writer.flush();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

        TestState testState = storageManager.loadState(TestState.class);
        TestState actual = new TestState();
        actual.setValues();
        Assert.assertEquals(actual, testState);
        //noinspection ResultOfMethodCallIgnored
        saveFile.delete();
    }

    @State(
            name = "TestState",
            storages = @Storage(
                    storageClass = DefaultTestStageStorage.class
            )
    )
    private static class TestState {

        private String testString;
        private int testInt;
        private float testFloat;
        private long testLong;
        private double testDouble;
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private List<Object> testList = new LinkedList<>();
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private Map<Object, Object> testMap = new HashMap<>();

        void setValues() {
            testString = "Test";
            testInt = 2843138;
            testFloat = 13843.143848343f;
            testLong = 2834346478494576546L;
            testDouble = 4387647676767676.767647676874d;
            testList.add(testString);
            testList.add(testInt);
            testList.add(testFloat);
            testList.add(testLong);
            testList.add(testDouble);
            testMap.put(testString, testString);
            testMap.put(testInt, testInt);
            testMap.put(testFloat, testFloat);
            testMap.put(testLong, testLong);
            testMap.put(testDouble, testDouble);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TestState)) {
                return false;
            }
            TestState obj1 = (TestState) obj;
            return testString == null ? obj1.testString == null : testString.equals(obj1.testString) &&
                    testInt == obj1.testInt &&
                    testFloat == obj1.testFloat &&
                    testLong == obj1.testLong &&
                    testDouble == obj1.testDouble &&
                    // We can't use equals as loaded values may have other types (e.g. int > double)
                    testMap.size() == obj1.testMap.size() &&
                    testList.size() == obj1.testList.size();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class DefaultTestStageStorage extends DefaultStateStorage {

        @NotNull
        @Override
        protected String getStorageDirectory() {
            return System.getProperty("java.io.tmpdir");
        }
    }
}
