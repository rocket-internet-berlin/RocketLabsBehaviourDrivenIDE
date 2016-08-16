package de.rocketlabs.behatide.modules.behat;

import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.ProjectLoader;
import de.rocketlabs.behatide.domain.model.ProjectType;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.AbstractModule;
import de.rocketlabs.behatide.modules.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;

import java.util.LinkedList;
import java.util.List;

public class BehatModule extends AbstractModule {

    private transient List<ProjectType> projectSetups;

    @Override
    protected void configure() {
        bind(Configuration.class).to(BehatConfiguration.class);
        bind(ConfigurationReader.class).to(BehatConfigurationReader.class);
        bind(ProjectLoader.class).to(de.rocketlabs.behatide.modules.behat.model.ProjectLoader.class);
    }

    @Override
    public List<ProjectType> getProjectTypes() {
        if (projectSetups == null) {
            projectSetups = new LinkedList<>();
            projectSetups.add(new ProjectType("Behat Project", null, new ProjectConfiguration()));
        }
        return projectSetups;
    }
}
