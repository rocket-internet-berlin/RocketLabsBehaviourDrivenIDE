package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.MenuBar;

public class MainMenuBar extends MenuBar implements FxmlLoading {

    private ObjectProperty<Project> project = new SimpleObjectProperty<>();

    public MainMenuBar() {
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/MenuBar.fxml";
    }

    public Project getProject() {
        return project.get();
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }
}
