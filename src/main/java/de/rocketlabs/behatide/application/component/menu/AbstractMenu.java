package de.rocketlabs.behatide.application.component.menu;

import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

class AbstractMenu extends Menu {

    protected ObjectProperty<Project> project = new SimpleObjectProperty<>();

    AbstractMenu() {
        super();
    }

    AbstractMenu(String text) {
        super(text);
    }

    AbstractMenu(String text, Node graphic) {
        super(text, graphic);
    }

    AbstractMenu(String text, Node graphic, MenuItem... items) {
        super(text, graphic, items);
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
