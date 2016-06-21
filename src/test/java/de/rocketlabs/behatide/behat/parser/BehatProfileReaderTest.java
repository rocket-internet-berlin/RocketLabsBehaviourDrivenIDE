package de.rocketlabs.behatide.behat.parser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.behat.BehatModule;
import de.rocketlabs.behatide.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.behat.model.BehatProfile;
import de.rocketlabs.behatide.behat.model.BehatSuite;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BehatProfileReaderTest extends TestCase {

    private ConfigurationReader configurationReader;

    @Override
    protected void setUp() throws Exception {
        Injector injector = Guice.createInjector(new BehatModule());
        configurationReader = injector.getInstance(ConfigurationReader.class);
    }

    public void testBasicConfiguration() throws IOException {
        InputStream resource = getClass().getResourceAsStream("testBasicConfiguration.behat.yml");
        Object configuration = configurationReader.read(resource);
        Assert.assertTrue("Configuration reader returned wrong data type", configuration instanceof BehatConfiguration);

        BehatConfiguration behatConfiguration = (BehatConfiguration) configuration;
        Assert.assertEquals(1, behatConfiguration.getProfileNames().size());
        Assert.assertTrue(behatConfiguration.getProfileNames().contains("default"));
        BehatProfile profile = behatConfiguration.getProfile("default");

        Assert.assertEquals("en", profile.getFallbackLocale());
        Assert.assertEquals("en", profile.getLocale());
        Assert.assertEquals(1, profile.getAutoLoadPaths().size());
        Assert.assertEquals("%paths.base%/features", profile.getAutoLoadPaths().get(0));
        Assert.assertEquals("default", profile.getName());
        Assert.assertEquals(1, profile.getFilters().size());
        Assert.assertEquals(1, profile.getSuiteNames().size());
        Assert.assertNotNull(profile.getSuite("default"));
    }

    public void testSuiteConfiguration() throws IOException {
        InputStream resource = getClass().getResourceAsStream("testSuiteConfiguration.behat.yml");
        Object configuration = configurationReader.read(resource);
        Assert.assertTrue("Configuration reader returned wrong data type", configuration instanceof BehatConfiguration);

        BehatConfiguration behatConfiguration = (BehatConfiguration) configuration;
        Assert.assertEquals(1, behatConfiguration.getProfileNames().size());
        Assert.assertTrue(behatConfiguration.getProfileNames().contains("default"));
        BehatProfile profile = behatConfiguration.getProfile("default");

        Assert.assertEquals(3, profile.getSuiteNames().size());
        Assert.assertTrue(profile.getSuiteNames().contains("default"));
        Assert.assertTrue(profile.getSuiteNames().contains("second"));
        Assert.assertTrue(profile.getSuiteNames().contains("third"));

        BehatSuite defaultSuite = profile.getSuite("default");
        BehatSuite secondSuite = profile.getSuite("second");
        BehatSuite thirdSuite = profile.getSuite("third");

        Assert.assertEquals(3, defaultSuite.getSettingKeys().size());
        Assert.assertTrue(defaultSuite.getSettingKeys().contains("contexts"));
        Assert.assertTrue(defaultSuite.getSettingKeys().contains("filters"));
        Assert.assertTrue(defaultSuite.getSettingKeys().contains("paths"));
        Assert.assertTrue(defaultSuite.isEnabled());
        Assert.assertNull(defaultSuite.getType());

        Assert.assertEquals(2, secondSuite.getSettingKeys().size());
        Assert.assertTrue(secondSuite.getSettingKeys().contains("filters"));
        Assert.assertTrue(secondSuite.getSettingKeys().contains("paths"));
        Assert.assertFalse(secondSuite.isEnabled());
        Assert.assertNull(secondSuite.getType());

        Assert.assertEquals(2, thirdSuite.getSettingKeys().size());
        Assert.assertTrue(thirdSuite.getSettingKeys().contains("contexts"));
        Assert.assertTrue(thirdSuite.getSettingKeys().contains("paths"));
        Assert.assertTrue(thirdSuite.isEnabled());
        Assert.assertEquals("suite", thirdSuite.getType());
    }

    public void testYamlLinks() throws IOException {
        InputStream resource = getClass().getResourceAsStream("testYmlLinks.behat.yml");
        Object configuration = configurationReader.read(resource);
        Assert.assertTrue("Configuration reader returned wrong data type", configuration instanceof BehatConfiguration);

        BehatConfiguration behatConfiguration = (BehatConfiguration) configuration;
        Assert.assertEquals(3, behatConfiguration.getProfileNames().size());
        Assert.assertTrue(behatConfiguration.getProfileNames().contains("default"));
        Assert.assertTrue(behatConfiguration.getProfileNames().contains("secondProfile"));
        Assert.assertTrue(behatConfiguration.getProfileNames().contains("thirdProfile"));

        BehatProfile defaultProfile = behatConfiguration.getProfile("default");
        BehatProfile secondProfile = behatConfiguration.getProfile("secondProfile");
        BehatProfile thirdProfile = behatConfiguration.getProfile("thirdProfile");

        Assert.assertEquals(1, defaultProfile.getAutoLoadPaths().size());
        Assert.assertEquals(1, secondProfile.getAutoLoadPaths().size());
        Assert.assertEquals(1, thirdProfile.getAutoLoadPaths().size());
        Assert.assertTrue(thirdProfile.getAutoLoadPaths().contains("%paths.base%/features/bootstrap"));

        Assert.assertEquals(1, defaultProfile.getSuiteNames().size());
        Assert.assertEquals(1, secondProfile.getSuiteNames().size());
        Assert.assertEquals(1, thirdProfile.getSuiteNames().size());

        Assert.assertEquals(1, defaultProfile.getFilters().size());
        Assert.assertEquals(2, secondProfile.getFilters().size());
        Assert.assertEquals(0, thirdProfile.getFilters().size());

        Assert.assertEquals(1, defaultProfile.getSuiteNames().size());
        Assert.assertEquals(1, secondProfile.getSuiteNames().size());
        Assert.assertEquals(1, thirdProfile.getSuiteNames().size());

        Assert.assertTrue(defaultProfile.getSuiteNames().contains("default"));
        Assert.assertTrue(secondProfile.getSuiteNames().contains("default"));
        Assert.assertTrue(thirdProfile.getSuiteNames().contains("default"));

        BehatSuite defaultSuite = defaultProfile.getSuite("default");
        BehatSuite secondSuite = secondProfile.getSuite("default");
        BehatSuite thirdSuite = thirdProfile.getSuite("default");

        Assert.assertEquals(1, defaultSuite.getSettingKeys().size());
        Assert.assertEquals(1, secondSuite.getSettingKeys().size());
        Assert.assertEquals(1, thirdSuite.getSettingKeys().size());

        Assert.assertTrue(defaultSuite.getSettingKeys().contains("paths"));
        Assert.assertTrue(secondSuite.getSettingKeys().contains("paths"));
        Assert.assertTrue(thirdSuite.getSettingKeys().contains("paths"));

        Assert.assertTrue(defaultSuite.getSetting("paths") instanceof List);
        Assert.assertTrue(secondSuite.getSetting("paths") instanceof List);
        Assert.assertTrue(thirdSuite.getSetting("paths") instanceof List);

        Assert.assertEquals("somePath", ((List) defaultSuite.getSetting("paths")).get(0));
        Assert.assertEquals("noPath", ((List) secondSuite.getSetting("paths")).get(0));
        Assert.assertEquals("somePath", ((List) thirdSuite.getSetting("paths")).get(0));
    }
}
