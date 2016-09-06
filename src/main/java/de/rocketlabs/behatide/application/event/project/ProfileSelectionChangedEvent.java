package de.rocketlabs.behatide.application.event.project;

import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Project;

public class ProfileSelectionChangedEvent implements Event {

    private final Project project;
    private final Profile profile;

    public ProfileSelectionChangedEvent(Project project, Profile profile) {
        this.project = project;
        this.profile = profile;
    }

    public Project getProject() {
        return project;
    }

    public Profile getProfile() {
        return profile;
    }
}
