package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import org.jetbrains.annotations.NotNull;

public class ProjectLoadedEvent implements Event {

    private ProjectMetaData projectModel;

    public ProjectLoadedEvent(@NotNull ProjectMetaData projectModel) {
        this.projectModel = projectModel;
    }

    public ProjectMetaData getProjectMetaData() {
        return projectModel;
    }
}
