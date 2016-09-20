package de.rocketlabs.behatide.php.model;

import de.rocketlabs.behatide.application.util.Collectors;
import de.rocketlabs.behatide.domain.model.Definition;
import de.rocketlabs.behatide.domain.model.DefinitionContainer;

import java.util.List;

public class PhpClass implements DefinitionContainer {

    private final String name;
    private final List<PhpFunction> members;

    public PhpClass(String name, List<PhpFunction> members) {
        this.name = name;
        this.members = members;
    }

    @Override
    public List<Definition> getDefinitions() {
        return members.stream()
                      .filter(f -> f.getAnnotations().size() > 0)
                      .collect(Collectors.toLinkedList());
    }

    @Override
    public String getName() {
        return name;
    }
}
