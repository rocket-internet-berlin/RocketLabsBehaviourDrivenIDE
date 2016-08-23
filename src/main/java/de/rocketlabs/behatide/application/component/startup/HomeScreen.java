package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.component.startup.wizard.CreateProjectWizard;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.ProjectConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeScreen extends BorderPane {

    @FXML
    private Label version;

    public HomeScreen() {
        super();
        loadComponent();
    }

    private void loadComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/startup/HomeScreen.fxml"));
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
        version.setText(getVersion());
    }

    @FXML
    private String getVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        if (version == null) {
            return "DEVELOPMENT VERSION";
        }
        return version;
    }

    @FXML
    private void onCreateNewProject() {
        CreateProjectWizard wizard = new CreateProjectWizard();
        wizard.showAndWait().ifPresent(result -> {
            Object userData = wizard.getUserData();
            if (userData instanceof ProjectConfiguration) {
                Project project = ((ProjectConfiguration) userData).createProject();
                EventManager.fireEvent(new LoadProjectEvent(project.getMetaData()));
            }
        });
    }
}