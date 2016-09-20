package de.rocketlabs.behatide.domain.model;

import java.util.List;

public interface Definition {

    String getMethodName();

    String getDescription();

    List<DefinitionAnnotation> getAnnotations();
}
