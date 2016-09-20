package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.domain.model.Project;

public class FileSaveRequestEvent implements Event {

    private Project project;

    public FileSaveRequestEvent(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
