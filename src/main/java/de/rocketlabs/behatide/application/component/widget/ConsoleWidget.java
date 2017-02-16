package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.control.Console;
import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class ConsoleWidget extends Widget {

    public TabPane pane;
    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();

    public ConsoleWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/widget/ConsoleWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Console newConsole(String name) {
        Console console = new Console();
        Tab tab = new Tab(name, console);
        pane.getTabs().add(tab);
        pane.getSelectionModel().select(tab);
        return console;
    }

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
    }
}
