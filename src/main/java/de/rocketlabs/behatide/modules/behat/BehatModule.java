package de.rocketlabs.behatide.modules.behat;

import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.ProjectConfiguration;
import de.rocketlabs.behatide.domain.model.ProjectLoader;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.AbstractModule;
import de.rocketlabs.behatide.modules.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;

import java.awt.*;

public class BehatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).to(BehatConfiguration.class);
        bind(ConfigurationReader.class).to(BehatConfigurationReader.class);
        bind(ProjectLoader.class).to(de.rocketlabs.behatide.modules.behat.model.ProjectLoader.class);
    }

    @Override
    public String getProjectTypeName() {
        return "Behat Project";
    }

    @Override
    public Image getProjectTypeIcon() {
        return null;
    }

    @Override
    public ProjectConfiguration getDefaultProjectConfiguration() {
        return new de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration();
    }
}
