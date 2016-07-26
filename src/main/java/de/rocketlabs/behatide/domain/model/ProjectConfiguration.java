package de.rocketlabs.behatide.domain.model;

import de.rocketlabs.behatide.domain.ConfigurationField;

import java.util.List;

public interface ProjectConfiguration {

    List<ConfigurationField> getFields();
}
