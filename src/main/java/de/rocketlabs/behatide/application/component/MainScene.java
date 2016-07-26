package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.domain.model.Project;
import javafx.scene.layout.BorderPane;

public class MainScene extends BorderPane {

    private Project project;

    public void setProject(Project project) {
        this.project = project;
    }
}
