package de.rocketlabs.behatide.behat.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.behat.BehatModule;
import de.rocketlabs.behatide.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;

public class BehatProfileReaderTest extends TestCase {

    private Injector injector;

    @Override
    protected void setUp() throws Exception {
        injector = Guice.createInjector(new BehatModule());
    }

    public void testBasicConfiguration() throws IOException {
        ConfigurationReader configurationReader = injector.getInstance(ConfigurationReader.class);
        InputStream resource = getClass().getResourceAsStream("testBasicConfiguration.behat.yml");
        Object configuration = configurationReader.read(resource);
        Assert.assertTrue("Configuration reader returned wrong data type", configuration instanceof BehatConfiguration);
    }
}
