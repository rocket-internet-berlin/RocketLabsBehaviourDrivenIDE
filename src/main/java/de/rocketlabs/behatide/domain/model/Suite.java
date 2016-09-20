package de.rocketlabs.behatide.domain.model;

import java.util.List;

public interface Suite {

    List<String> getPaths();

    String getName();

    List<String> getDefinitionContainerIdentifiers();
}
