package de.rocketlabs.behatide.application.event;

import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.Definition;

public class DefinitionClickedEvent implements Event {

    private final Definition definition;
    private final ProjectContext projectContext;

    public DefinitionClickedEvent(Definition definition, ProjectContext projectContext) {
        this.definition = definition;
        this.projectContext = projectContext;
    }

    public Definition getDefinition() {
        return definition;
    }

    public ProjectContext getProject() {
        return projectContext;
    }
}
