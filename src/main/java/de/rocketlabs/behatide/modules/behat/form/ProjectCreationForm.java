package de.rocketlabs.behatide.modules.behat.form;

import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration;

public class ProjectCreationForm extends IdeForm {

    private ProjectConfiguration configuration;

    public ProjectCreationForm(ProjectConfiguration configuration) {
        this.configuration = configuration;

        initFields();
    }

    private void initFields() {


        addControl("Configuration File", null);
        addControl("Behat executable", null);
        addControl("Project location", null);
    }

}
