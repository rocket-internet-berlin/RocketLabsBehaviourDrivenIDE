package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

public class CloseProjectEvent implements Event {

    private ProjectMetaData projectModel;

    public CloseProjectEvent(ProjectMetaData projectModel) {
        this.projectModel = projectModel;
    }

    public ProjectMetaData getProjectModel() {
        return projectModel;
    }
}
