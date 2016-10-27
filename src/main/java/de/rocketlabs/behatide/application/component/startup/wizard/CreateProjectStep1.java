package de.rocketlabs.behatide.application.component.startup.wizard;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.application.component.startup.wizard.step1.ProjectItem;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.application.wizard.ValidationWizardStep;
import de.rocketlabs.behatide.domain.model.ProjectConfiguration;
import de.rocketlabs.behatide.domain.model.ProjectType;
import de.rocketlabs.behatide.modules.AbstractModule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import org.controlsfx.dialog.WizardPane;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class CreateProjectStep1 extends WizardPane implements ValidationWizardStep, FxmlLoading {

    @FXML
    private ListView<ProjectType> projectTypeList;
    @FXML
    private BorderPane pane;
    private BooleanProperty invalidProperty = new SimpleBooleanProperty(true);

    CreateProjectStep1() {
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/startup/createProjectWizard/step1.fxml";
    }

    @FXML
    private void initialize() {
        ModuleManager moduleManager = ModuleManager.getInstance();
        List<AbstractModule> loadedModules = moduleManager.getLoadedModules();
        List<ProjectType> knownTypes = new LinkedList<>();
        for (AbstractModule module : loadedModules) {
            knownTypes.addAll(module.getProjectTypes());
        }

        projectTypeList.setCellFactory(ProjectItem::new);
        projectTypeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectTypeList.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        projectTypeList.setItems(FXCollections.observableList(knownTypes));
    }

    private void selectionChanged(ObservableValue<? extends ProjectType> observable,
                                  ProjectType oldValue,
                                  ProjectType newValue) {
        ProjectConfiguration<?> configuration = newValue.getDefaultConfiguration().getClone();
        IdeForm form = configuration.getForm();
        pane.setCenter(form);
        invalidProperty.unbind();
        invalidProperty.bind(form.getValidation().invalidProperty());
        setUserData(configuration);
    }

    @Override
    @NotNull
    public ReadOnlyBooleanProperty invalidProperty() {
        return invalidProperty;
    }
}



