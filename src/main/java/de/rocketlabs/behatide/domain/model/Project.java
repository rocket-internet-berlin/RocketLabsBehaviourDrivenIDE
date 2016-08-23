package de.rocketlabs.behatide.domain.model;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

public abstract class Project {

    public abstract String getTitle();

    public abstract ProjectMetaData getMetaData();
}
