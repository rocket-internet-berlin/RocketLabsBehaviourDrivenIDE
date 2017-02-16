package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Suite;

public class SuiteSelectionChangedEvent implements Event {

    private final ProjectContext projectContext;
    private Profile profile;
    private final Suite suite;

    public SuiteSelectionChangedEvent(ProjectContext projectContext, Profile profile, Suite suite) {
        this.projectContext = projectContext;
        this.profile = profile;
        this.suite = suite;
    }

    public ProjectContext getProjectContext() {
        return projectContext;
    }

    public Profile getProfile() {
        return profile;
    }

    public Suite getSuite() {
        return suite;
    }
}
