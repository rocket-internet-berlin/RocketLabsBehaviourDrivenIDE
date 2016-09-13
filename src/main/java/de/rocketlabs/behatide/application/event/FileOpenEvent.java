package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.domain.model.Project;

import java.io.File;

public class FileOpenEvent implements Event {

    private final File item;
    private final Project project;

    public FileOpenEvent(File item, Project project) {
        this.item = item;
        this.project = project;
    }

    public File getItem() {
        return item;
    }

    public Project getProject() {
        return project;
    }
}
