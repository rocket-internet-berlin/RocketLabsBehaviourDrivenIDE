package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.php.model.PhpFunction;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@State(name = "Project")
public class Project implements de.rocketlabs.behatide.domain.model.Project {

    private String title;
    private String projectLocation;
    private String behatExecutablePath;
    private String behatConfigurationFile;
    private BehatConfiguration configuration;
    private Map<String, List<PhpFunction>> availableDefinitions;
    private byte[] configurationFileHash;

    public Map<String, List<PhpFunction>> getAvailableDefinitions() {
        return availableDefinitions;
    }

    public void setAvailableDefinitions(Map<String, List<PhpFunction>> availableDefinitions) {
        this.availableDefinitions = availableDefinitions;
    }

    public String getBehatConfigurationFile() {
        return behatConfigurationFile;
    }

    public void setBehatConfigurationFile(String behatConfigurationFile) {
        this.behatConfigurationFile = behatConfigurationFile;
    }

    public String getBehatExecutablePath() {
        return behatExecutablePath;
    }

    public void setBehatExecutablePath(String behatExecutablePath) {
        this.behatExecutablePath = behatExecutablePath;
    }

    public byte[] getConfigurationFileHash() {
        return configurationFileHash;
    }

    public void setConfigurationFileHash(byte[] configurationFileHash) {
        this.configurationFileHash = configurationFileHash;
    }

    @Override
    public String getFileMask() {
        return ".*\\.feature$";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Map<String, String> getPathReplacements() {
        return new HashMap<String, String>() {{
            put("%paths.base%", new File(behatConfigurationFile).getParentFile().getAbsolutePath());
        }};
    }

    @Override
    public ProjectMetaData getMetaData() {
        return new ProjectMetaData(projectLocation, title, "BehatModule");
    }

    @Override
    public BehatConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(BehatConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }
}
