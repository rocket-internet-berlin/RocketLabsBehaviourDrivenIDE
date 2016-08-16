package de.rocketlabs.behatide.application.component.startup.wizard;

import de.rocketlabs.behatide.application.component.IdeForm;
import de.rocketlabs.behatide.application.component.startup.wizard.step1.ProjectItem;
import de.rocketlabs.behatide.application.configuration.storage.StateStorageManager;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.domain.model.ProjectConfiguration;
import de.rocketlabs.behatide.domain.model.ProjectType;
import de.rocketlabs.behatide.modules.AbstractModule;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import org.controlsfx.dialog.WizardPane;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CreateProjectStep1 extends WizardPane {

    @FXML
    private ListView<ProjectType> projectTypeList;
    @FXML
    private Pane editorPane;

    CreateProjectStep1() {
        loadComponent();
    }

    private void loadComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/startup/createProjectWizard/step1.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        ModuleManager moduleManager = StateStorageManager.getInstance().loadState(ModuleManager.class);
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
    }

}



