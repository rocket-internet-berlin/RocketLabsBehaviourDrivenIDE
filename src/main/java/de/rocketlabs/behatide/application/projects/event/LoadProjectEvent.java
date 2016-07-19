package de.rocketlabs.behatide.application.projects.event;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.application.projects.RecentProjectModel;
import javafx.stage.Stage;

public class LoadProjectEvent implements Event {

    private Stage stage;
    private RecentProjectModel projectModel;

    public LoadProjectEvent(RecentProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public LoadProjectEvent(RecentProjectModel projectModel, Stage stage) {
        this.projectModel = projectModel;
        this.stage = stage;
    }

    RecentProjectModel getProjectModel() {
        return projectModel;
    }

    Stage getStage() {
        return stage;
    }
}
