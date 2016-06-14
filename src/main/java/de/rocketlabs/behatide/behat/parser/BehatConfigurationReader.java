package de.rocketlabs.behatide.behat.parser;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.inject.Inject;
import de.rocketlabs.behatide.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;

import java.io.*;
import java.util.Map;

public final class BehatConfigurationReader implements ConfigurationReader<BehatConfiguration> {

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
