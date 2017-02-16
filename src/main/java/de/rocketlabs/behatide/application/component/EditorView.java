package de.rocketlabs.behatide.application.component;

import com.google.inject.Guice;
import de.rocketlabs.behatide.application.component.control.Console;
import de.rocketlabs.behatide.application.component.widget.ConsoleWidget;
import de.rocketlabs.behatide.application.editor.component.Editor;
import de.rocketlabs.behatide.application.editor.event.ProfileSelectionChangedEvent;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.application.model.ProjectContext;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.nio.file.Path;

public class EditorView extends BorderPane implements FxmlLoading {

    @FXML
    private ConsoleWidget consoleWidget;
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

    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();

    public EditorView() {
        loadFxml();

        projectContext.addListener((observable, oldValue, newContext) -> {
            Configuration configuration = newContext.getProject().getConfiguration();
            profileSelection.setItems(FXCollections.observableList(configuration.getProfiles()));
        });

        initProfileSelection();
        initSuiteSelection();
    }

    @Override
    public String getFxmlPath() {
        return "/view/EditorView.fxml";
    }

    private void initSuiteSelection() {
        suiteSelection.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newSuite)
                -> EventManager.fireEvent(new SuiteSelectionChangedEvent(
                getProjectContext(),
                profileSelection.getValue(),
                newSuite
            ))
        );
        EventManager.addListener(
            ProfileSelectionChangedEvent.class,
            e -> {
                if (e.getProjectContext().equals(getProjectContext())) {
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
                getProjectContext(),
                newProfile
            )));
        profileSelection.setButtonCell(new ProfileCell());
        profileSelection.setCellFactory(ProfileCell::new);
        profileSelection.setPlaceholder(new Label("No Profiles found"));
    }

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    @FXML
    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
    }

    @FXML
    private void runFile() {
        Project project = getProjectContext().getProject();
        Suite suite = suiteSelection.getSelectionModel().getSelectedItem();
        Profile profile = profileSelection.getSelectionModel().getSelectedItem();
        Path file = editor.getOpenFilePath();

        AbstractModule module = ModuleManager.getInstance().forName
            (project.getMetaData().getModuleName());

        TestRunner runner = Guice.createInjector(module).getInstance(TestRunner.class);
        Console console = consoleWidget.newConsole(file.getFileName().toString());
        //noinspection unchecked
        runner.runFile(project, profile, suite, file, console);
        //TODO: Show console widget
    }

    public Editor getEditor() {
        return editor;
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
