package de.rocketlabs.behatide.modules.behat;

import de.rocketlabs.behatide.application.configuration.storage.GsonUtils;
import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.ProjectLoader;
import de.rocketlabs.behatide.domain.model.ProjectType;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.domain.runner.TestRunner;
import de.rocketlabs.behatide.modules.AbstractModule;
import de.rocketlabs.behatide.modules.behat.filter.*;
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
        bind(Project.class).to(de.rocketlabs.behatide.modules.behat.model.Project.class);
        bind(TestRunner.class).to(de.rocketlabs.behatide.modules.behat.runner.TestRunner.class);
    }

    @Override
    public List<ProjectType> getProjectTypes() {
        if (projectSetups == null) {
            projectSetups = new LinkedList<>();
            projectSetups.add(new ProjectType("Behat Project", null, new ProjectConfiguration()));
        }
        return projectSetups;
    }

    @Override
    public void configureGson() {
        GsonUtils.registerType(AbstractModule.class, getClass());
        GsonUtils.registerType(Project.class, de.rocketlabs.behatide.modules.behat.model.Project.class);
        GsonUtils.registerType(
                GherkinFilter.class,
                NameFilter.class,
                NarrativeFilter.class,
                RoleFilter.class,
                TagFilter.class
        );
    }
}
