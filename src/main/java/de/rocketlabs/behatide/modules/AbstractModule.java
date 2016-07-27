package de.rocketlabs.behatide.modules;

import de.rocketlabs.behatide.domain.model.ProjectConfiguration;

import java.awt.*;

public abstract class AbstractModule extends com.google.inject.AbstractModule {

    public abstract String getProjectTypeName();

    public abstract Image getProjectTypeIcon();

    public abstract ProjectConfiguration getDefaultProjectConfiguration();
}
