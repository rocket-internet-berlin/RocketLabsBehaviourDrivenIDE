package de.rocketlabs.behatide.modules.behat.form;

import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Validator;

public class ProjectCreationForm extends IdeForm {

    public static final String ERR_MSG_EMPTY = "Value cannot be empty";
    private ProjectConfiguration configuration;

    public ProjectCreationForm(ProjectConfiguration configuration) {
        this.configuration = configuration;

        initFields();
    }

    private void initFields() {
        TextField title = new TextField();
        title.textProperty().bindBidirectional(configuration.titleProperty());
        addControl("Title", title);

        TextField configFile = new TextField();
        configFile.textProperty().bindBidirectional(configuration.behatConfigurationFileProperty());
        addControl("Configuration File", configFile);

        TextField executable = new TextField();
        executable.textProperty().bindBidirectional(configuration.behatExecutableProperty());
        addControl("Behat executable", executable);

        TextField projectLocation = new TextField();
        projectLocation.textProperty().bindBidirectional(configuration.projectLocationProperty());
        addControl("Project location", projectLocation);

        getValidation().registerValidator(title, Validator.createEmptyValidator(ERR_MSG_EMPTY));
        getValidation().registerValidator(configFile, Validator.createEmptyValidator(ERR_MSG_EMPTY));
        getValidation().registerValidator(executable, Validator.createEmptyValidator(ERR_MSG_EMPTY));
        getValidation().registerValidator(projectLocation, Validator.createEmptyValidator(ERR_MSG_EMPTY));
        getValidation().setErrorDecorationEnabled(false);
    }

}
