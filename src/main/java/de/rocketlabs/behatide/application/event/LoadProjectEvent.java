package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import org.jetbrains.annotations.NotNull;

public class LoadProjectEvent implements Event {

    private ProjectMetaData projectModel;

    public LoadProjectEvent(@NotNull ProjectMetaData projectModel) {
        this.projectModel = projectModel;
    }

    public ProjectMetaData getProjectMetaData() {
        return projectModel;
    }

}
