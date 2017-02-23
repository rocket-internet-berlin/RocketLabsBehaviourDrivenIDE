package de.rocketlabs.behatide.application.component.menu;

import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

class AbstractMenu extends Menu {

    protected ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();

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

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    public void setProjectContext(ProjectContext project) {
        this.projectContext.set(project);
    }
}
