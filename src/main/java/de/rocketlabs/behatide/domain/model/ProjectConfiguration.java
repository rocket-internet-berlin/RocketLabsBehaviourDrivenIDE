package de.rocketlabs.behatide.domain.model;

import de.rocketlabs.behatide.application.component.IdeForm;

public interface ProjectConfiguration<T extends ProjectConfiguration> extends Cloneable {

    T getClone();

    IdeForm getForm();
}
