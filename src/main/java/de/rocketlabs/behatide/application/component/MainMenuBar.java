package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class MainMenuBar extends MenuBar implements FxmlLoading {

    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();

    public MainMenuBar() {
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/MenuBar.fxml";
    }

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
    }

    @FXML
    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }
}
