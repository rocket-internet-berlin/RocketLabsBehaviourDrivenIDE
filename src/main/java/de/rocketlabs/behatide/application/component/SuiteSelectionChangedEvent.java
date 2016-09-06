package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.Suite;

public class SuiteSelectionChangedEvent implements Event {

    private final Project project;
    private final Suite suite;

    public SuiteSelectionChangedEvent(Project project, Suite suite) {
        this.project = project;
        this.suite = suite;
    }

    public Project getProject() {
        return project;
    }

    public Suite getSuite() {
        return suite;
    }
}
