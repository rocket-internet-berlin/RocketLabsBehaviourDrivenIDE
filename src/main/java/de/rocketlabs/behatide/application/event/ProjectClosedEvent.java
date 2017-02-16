package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

public class ProjectClosedEvent implements Event {

    private ProjectMetaData projectModel;

    public ProjectClosedEvent(ProjectMetaData projectModel) {
        this.projectModel = projectModel;
    }

    public ProjectMetaData getProjectModel() {
        return projectModel;
    }
}
