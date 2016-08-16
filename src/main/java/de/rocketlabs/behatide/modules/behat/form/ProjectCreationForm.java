package de.rocketlabs.behatide.modules.behat.form;

import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Validator;

public class ProjectCreationForm extends IdeForm {

    private ProjectConfiguration configuration;

    public ProjectCreationForm(ProjectConfiguration configuration) {
        this.configuration = configuration;

        initFields();
    }

    private void initFields() {
        TextField configFile = new TextField();
        configFile.textProperty().bindBidirectional(configuration.behatConfigurationFileProperty());
        addControl("Configuration File", configFile);

        TextField executable = new TextField();
        executable.textProperty().bindBidirectional(configuration.behatExecutableProperty());
        addControl("Behat executable", executable);

        TextField projectLocation = new TextField();
        projectLocation.textProperty().bindBidirectional(configuration.projectLocationProperty());
        addControl("Project location", projectLocation);


        getValidation().registerValidator(configFile, Validator.createEmptyValidator("Value cannot be empty"));
        getValidation().registerValidator(executable, Validator.createEmptyValidator("Value cannot be empty"));
        getValidation().registerValidator(projectLocation, Validator.createEmptyValidator("Value cannot be empty"));
        getValidation().setErrorDecorationEnabled(false);
    }

}
