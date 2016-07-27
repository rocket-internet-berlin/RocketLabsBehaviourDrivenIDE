package de.rocketlabs.behatide.modules.behat.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;

import java.io.File;
import java.io.IOException;

public class ProjectLoader implements de.rocketlabs.behatide.domain.model.ProjectLoader<Project> {

    private final BehatConfigurationReader configurationReader;

    public ProjectLoader() {
        Injector injector = Guice.createInjector(new BehatModule());
        configurationReader = (BehatConfigurationReader) injector.getInstance(ConfigurationReader.class);
    }

    @Override
    public Project loadProject(String configurationFilePath) {
        File file = new File(configurationFilePath);

        BehatConfiguration behatConfiguration;
        try {
            behatConfiguration = configurationReader.read(file);
        } catch (IOException e) {
            EventManager.fireEvent(new FileLoadFailedEvent(file, e));
            return null;
        }

//        Map<String, Object> contextData = new HashMap<>();
//        for (String profileName : behatConfiguration.getProfileNames()) {
//            BehatProfile profile = behatConfiguration.getProfile(profileName);
//            for (String suiteName : profile.getSuiteNames()) {
//                BehatSuite suite = profile.getSuite(suiteName);
//                if (suite.hasSetting("contexts")) {
//                    Object contexts = suite.getSetting("contexts");
//                    if (contexts instanceof List) {
//                        //noinspection unchecked
//                        for (String context : ((List<String>) contexts)) {
//                            if (contextData.containsKey(context)) {
//                                break;
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        }
        return null;
    }
}
