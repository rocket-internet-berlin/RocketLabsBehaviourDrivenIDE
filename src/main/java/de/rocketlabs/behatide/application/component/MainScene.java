package de.rocketlabs.behatide.application.component;

import com.google.inject.Guice;
import de.rocketlabs.behatide.application.component.editor.Editor;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.project.ProfileSelectionChangedEvent;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.Suite;
import de.rocketlabs.behatide.domain.runner.TestRunner;
import de.rocketlabs.behatide.modules.AbstractModule;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.nio.file.Path;

public class MainScene extends BorderPane {

    @FXML
    private Editor editor;
    @FXML
    private Button runButton;
    @FXML
    private MainMenuBar menuBar;
    @FXML
    private ComboBox<Profile> profileSelection;
    @FXML
    private ComboBox<Suite> suiteSelection;

    private ObjectProperty<Project> project = new SimpleObjectProperty<>();

    public MainScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        project.addListener((observable, oldValue, newProject) -> {
            Configuration configuration = newProject.getConfiguration();
            profileSelection.setItems(FXCollections.observableList(configuration.getProfiles()));
        });

        initProfileSelection();
        initSuiteSelection();
    }

    private void initSuiteSelection() {
        suiteSelection.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newSuite)
                -> EventManager.fireEvent(new SuiteSelectionChangedEvent(
                getProject(),
                profileSelection.getValue(),
                newSuite
            ))
        );
        EventManager.addListener(
            ProfileSelectionChangedEvent.class,
            e -> {
                if (e.getProject().equals(getProject())) {
                    suiteSelection.setItems(FXCollections.observableList(e.getProfile().getSuites()));
                }
            }
        );
        suiteSelection.setButtonCell(new SuiteCell());
        suiteSelection.setCellFactory(SuiteCell::new);
        suiteSelection.setPlaceholder(new Label("No Suites found"));
    }

    private void initProfileSelection() {
        profileSelection.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newProfile) -> EventManager.fireEvent(new ProfileSelectionChangedEvent(
                getProject(),
                newProfile
            )));
        profileSelection.setButtonCell(new ProfileCell());
        profileSelection.setCellFactory(ProfileCell::new);
        profileSelection.setPlaceholder(new Label("No Profiles found"));
    }

    public Project getProject() {
        return project.get();
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    @FXML
    private void runFile() {
        Project project = getProject();
        Suite suite = suiteSelection.getSelectionModel().getSelectedItem();
        Profile profile = profileSelection.getSelectionModel().getSelectedItem();
        Path file = editor.getOpenFilePath();

        AbstractModule module = ModuleManager.getInstance().forName
            (project.getMetaData().getModuleName());

        TestRunner runner = Guice.createInjector(module).getInstance(TestRunner.class);
        //noinspection unchecked
        runner.runFile(project, profile, suite, file);
    }

    private class ProfileCell extends ListCell<Profile> {

        ProfileCell() {
        }

        ProfileCell(ListView<Profile> profileListView) {
        }

        @Override
        protected void updateItem(Profile item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }

    private class SuiteCell extends ListCell<Suite> {

        SuiteCell() {
        }

        SuiteCell(ListView<Suite> profileListView) {
        }

        @Override
        protected void updateItem(Suite item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}
