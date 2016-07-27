package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.application.configuration.PathConfigurationField;
import de.rocketlabs.behatide.domain.ConfigurationField;

import java.util.LinkedList;
import java.util.List;

public class ProjectConfiguration implements de.rocketlabs.behatide.domain.model.ProjectConfiguration {

    private final PathConfigurationField behatConfigurationFile;
    private final PathConfigurationField projectLocation;
    private final PathConfigurationField behatExecutable;

    public ProjectConfiguration() {
        behatConfigurationFile = new PathConfigurationField("Configuration file");
        projectLocation = new PathConfigurationField("Project location");
        behatExecutable = new PathConfigurationField("Behat executable");
    }

    @Override
    public List<ConfigurationField> getFields() {
        return new LinkedList<ConfigurationField>() {{
            add(behatConfigurationFile);
            add(projectLocation);
            add(behatExecutable);
        }};
    }

    public String getBehatConfigurationFile() {
        return behatConfigurationFile.getData();
    }

    public String getProjectLocation() {
        return projectLocation.getData();
    }

    public String getBehatExecutable() {
        return behatExecutable.getData();
    }

    public void setBehatConfigurationFile(String path) {
        behatConfigurationFile.setData(path);
    }

    public void setProjectLocation(String path) {
        projectLocation.setData(path);
    }

    public void setBehatExecutable(String path) {
        behatExecutable.setData(path);
    }
}
