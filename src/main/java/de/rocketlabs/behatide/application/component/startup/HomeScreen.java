package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.component.startup.wizard.CreateProjectWizard;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.ProjectConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HomeScreen extends BorderPane implements FxmlLoading {

    @FXML
    private Label version;

    public HomeScreen() {
        super();
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/startup/HomeScreen.fxml";
    }

    @FXML
    private void initialize() {
        version.setText(getVersion());
    }

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
        wizard.showAndWait()
              .filter(predicate -> predicate.getButtonData() == ButtonBar.ButtonData.FINISH)
              .ifPresent(result -> {
                  Object userData = wizard.getUserData();
                  if (userData instanceof ProjectConfiguration) {
                      Project project = ((ProjectConfiguration) userData).createProject();
                      EventManager.fireEvent(new LoadProjectEvent(project.getMetaData()));
                  }
              });
    }
}
