package de.rocketlabs.behatide.modules.behat.parser;

import de.rocketlabs.behatide.modules.behat.model.BehatSuite;
import de.rocketlabs.behatide.modules.behat.parser.settings.BehatSettingsReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BehatSuiteReader {

    public static final String BEHAT_SUITE_KEY_ENABLED = "enabled";
    public static final String BEHAT_SUITE_KEY_TYPE = "type";
    public static final String BEHAT_SUITE_KEY_SETTINGS = "settings";

    private List<BehatSettingsReader> settingsReader = new LinkedList<>();

    public BehatSuite readSuite(String name, Object suiteData) {
        BehatSuite model = new BehatSuite(name);
        if (suiteData instanceof Map) {
            for (Object key : ((Map<?, ?>) suiteData).keySet()) {
                assert key instanceof String;
                Object value = ((Map) suiteData).get(key);
                switch (((String) key)) {
                    case BEHAT_SUITE_KEY_ENABLED:
                        model.setEnabled(Boolean.valueOf(value.toString()));
                        break;
                    case BEHAT_SUITE_KEY_TYPE:
                        model.setType(value.toString());
                        break;
                    case BEHAT_SUITE_KEY_SETTINGS:
                        assert value instanceof Map;
                        //noinspection unchecked
                        Map<String, Object> loadedData = readSettingsMap(((Map<String, Object>) value));

                        for (String key2 : loadedData.keySet()) {
                            model.addSetting(key2, loadedData.get(key2));
                        }
                        break;
                    default:
                        model.addSetting(
                            ((String) key),
                            readSettingsValue(((String) key), value)
                        );
                }
            }
        }
        return model;
    }

    private Map<String, Object> readSettingsMap(Map<String, Object> settings) {
        Map<String, Object> loadedSettings = new HashMap<>();
        for (String key : settings.keySet()) {
            loadedSettings.put(key, readSettingsValue(key, settings.get(key)));
        }
        return loadedSettings;
    }

    private Object readSettingsValue(String key, Object value) {
        for (BehatSettingsReader behatSettingsReader : settingsReader) {
            if (behatSettingsReader.supports(key)) {
                return behatSettingsReader.readSetting(key, value);
            }
        }
        return value;
    }
}
