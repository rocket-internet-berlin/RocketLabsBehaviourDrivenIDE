package de.rocketlabs.behatide.behat.parser;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.inject.Inject;
import de.rocketlabs.behatide.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class BehatConfigurationReader implements ConfigurationReader<BehatConfiguration> {

    public static final String YAML_HOOK_IDENTIFIER = "<<";
    private BehatProfileReader profileParser;

    @Inject
    public BehatConfigurationReader(BehatProfileReader profileParser) {
        this.profileParser = profileParser;
    }

    public BehatConfiguration read(InputStream fileStream) throws IOException {
        BehatConfiguration configuration = new BehatConfiguration();

        try (InputStreamReader fileReader = new InputStreamReader(fileStream)) {
            YamlReader reader = new YamlReader(fileReader);

            Object data = reader.read();
            data = mergeYamlLinks(data);
            if (data instanceof Map) {
                //noinspection Convert2streamapi
                for (Object profileName : ((Map) data).keySet()) {
                    if (profileName instanceof String) {
                        configuration.addProfile(profileParser.read(((String) profileName), (Map) ((Map) data).get(profileName)));
                    }
                }
            }
        }

        return configuration;
    }

    private Object mergeYamlLinks(Object data) {
        if (!(data instanceof Map)) {
            return data;
        }

        Map dataMap = (Map) data;
        //noinspection unchecked
        Map<String, Object> mergedMap = new HashMap<>(dataMap);

        if (mergedMap.containsKey(YAML_HOOK_IDENTIFIER)) {
            assert mergedMap.get(YAML_HOOK_IDENTIFIER) instanceof Map;

            //noinspection unchecked
            Map<String, Object> baseData = (Map) mergedMap.get(YAML_HOOK_IDENTIFIER);
            mergedMap.remove(YAML_HOOK_IDENTIFIER);

            mergedMap = mergeMapsRecursive(baseData, mergedMap);
        }

        //Start recursion
        for (String key : mergedMap.keySet()) {
            if (!key.equals(YAML_HOOK_IDENTIFIER)) {
                mergedMap.put(key, mergeYamlLinks(mergedMap.get(key)));
            }
        }
        return mergedMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mergeMapsRecursive(Map<String, Object> baseData, Map<String, Object> newData) {
        Map<String, Object> mergedMap = new HashMap<>(baseData);
        newData.forEach((newKey, newValue) -> {
            if (baseData.containsKey(newKey)) {
                Object baseValue = baseData.get(newKey);

                if (baseValue instanceof Collection) {
                    mergedMap.put(newKey, newValue);
                } else if (baseValue instanceof Map) {
                    assert newValue instanceof Map;

                    mergedMap.put(newKey, mergeMapsRecursive(((Map) baseValue), ((Map) newValue)));
                } else {
                    mergedMap.put(newKey, newValue);
                }
            } else {
                mergedMap.put(newKey, newValue);
            }
        });

        return mergedMap;
    }

    public BehatConfiguration read(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            throw new IOException("Cannot read directory: " + file.getAbsolutePath());
        }

        return read(new FileInputStream(file));
    }
}
