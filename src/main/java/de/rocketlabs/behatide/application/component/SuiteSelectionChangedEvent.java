package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.Suite;

public class SuiteSelectionChangedEvent implements Event {

    private final Project project;
    private Profile profile;
    private final Suite suite;

    public SuiteSelectionChangedEvent(Project project, Profile profile, Suite suite) {
        this.project = project;
        this.profile = profile;
        this.suite = suite;
    }

    public Project getProject() {
        return project;
    }

    public Profile getProfile() {
        return profile;
    }

    public Suite getSuite() {
        return suite;
    }
}
