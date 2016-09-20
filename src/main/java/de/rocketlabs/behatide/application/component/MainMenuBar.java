package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class MainMenuBar extends MenuBar {

    private ObjectProperty<Project> project = new SimpleObjectProperty<>();

    public MainMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Project getProject() {
        return project.get();
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }
}
