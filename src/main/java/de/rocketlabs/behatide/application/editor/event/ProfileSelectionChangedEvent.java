package de.rocketlabs.behatide.application.editor.event;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.Profile;

public class ProfileSelectionChangedEvent implements Event {

    private final ProjectContext projectContext;
    private final Profile profile;

    public ProfileSelectionChangedEvent(ProjectContext projectContext, Profile profile) {
        this.projectContext = projectContext;
        this.profile = profile;
    }

    public ProjectContext getProjectContext() {
        return projectContext;
    }

    public Profile getProfile() {
        return profile;
    }
}
