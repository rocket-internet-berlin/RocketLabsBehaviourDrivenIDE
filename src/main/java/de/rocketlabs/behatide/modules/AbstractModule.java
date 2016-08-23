package de.rocketlabs.behatide.modules;

import de.rocketlabs.behatide.domain.model.ProjectType;

import java.util.List;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    public abstract List<ProjectType> getProjectTypes();

    public abstract void configureGson();
}
