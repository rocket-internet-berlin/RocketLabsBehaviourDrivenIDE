package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.modules.behat.ProjectFactory;
import de.rocketlabs.behatide.modules.behat.form.ProjectCreationForm;
import javafx.beans.property.SimpleStringProperty;

public class ProjectConfiguration implements de.rocketlabs.behatide.domain.model
                                                     .ProjectConfiguration<ProjectConfiguration> {

    private final SimpleStringProperty behatConfigurationFile;
    private final SimpleStringProperty projectLocation;
    private final SimpleStringProperty behatExecutable;
    private final SimpleStringProperty title;

    public ProjectConfiguration() {
        behatConfigurationFile = new SimpleStringProperty();
        projectLocation = new SimpleStringProperty();
        behatExecutable = new SimpleStringProperty();
        title = new SimpleStringProperty();
    }

    public String getBehatConfigurationFile() {
        return behatConfigurationFile.get();
    }

    public SimpleStringProperty behatConfigurationFileProperty() {
        return behatConfigurationFile;
    }

    public void setBehatConfigurationFile(String behatConfigurationFile) {
        this.behatConfigurationFile.set(behatConfigurationFile);
    }

    public String getProjectLocation() {
        return projectLocation.get();
    }

    public SimpleStringProperty projectLocationProperty() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation.set(projectLocation);
    }

    public String getBehatExecutable() {
        return behatExecutable.get();
    }

    public SimpleStringProperty behatExecutableProperty() {
        return behatExecutable;
    }

    public void setBehatExecutable(String behatExecutable) {
        this.behatExecutable.set(behatExecutable);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public ProjectConfiguration getClone() {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration();
        projectConfiguration.setBehatConfigurationFile(getBehatConfigurationFile());
        projectConfiguration.setBehatExecutable(getBehatExecutable());
        projectConfiguration.setProjectLocation(getProjectLocation());
        projectConfiguration.setTitle(getTitle());
        return projectConfiguration;
    }

    @Override
    public IdeForm getForm() {
        return new ProjectCreationForm(this);
    }

    @Override
    public Project createProject() {
        return ProjectFactory.generateProject(this);
    }
}
