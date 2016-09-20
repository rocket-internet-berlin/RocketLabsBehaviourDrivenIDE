package de.rocketlabs.behatide.domain.model;

public interface DefinitionAnnotation {

    enum DefinitionAnnotationType {
        GIVEN, WHEN, THEN
    }

    DefinitionAnnotationType getType();

    String getStatement();
}
