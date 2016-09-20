package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.domain.model.Definition;
import de.rocketlabs.behatide.domain.model.Project;

public class DefinitionClickedEvent implements Event {

    private final Definition definition;
    private final Project project;

    public DefinitionClickedEvent(Definition definition, Project project) {
        this.definition = definition;
        this.project = project;
    }

    public Definition getDefinition() {
        return definition;
    }

    public Project getProject() {
        return project;
    }
}
