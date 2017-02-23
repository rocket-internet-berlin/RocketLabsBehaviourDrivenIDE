package de.rocketlabs.behatide.application.model;

import de.rocketlabs.behatide.application.editor.component.Editor;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jakob Erdmann
 * @since 13.02.17
 */
public class ProjectContext {
    private ObjectProperty<Project> project;
    private ObjectProperty<Editor> editor;

    public ProjectContext(Project project, Editor editor) {
        this.project = new SimpleObjectProperty<>(project);
        this.editor = new SimpleObjectProperty<>(editor);
    }

    public Project getProject() {
        return project.get();
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public Editor getEditor() {
        return editor.get();
    }

    public ObjectProperty<Editor> editorProperty() {
        return editor;
    }
}
