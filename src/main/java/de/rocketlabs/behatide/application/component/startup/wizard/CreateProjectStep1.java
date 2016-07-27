package de.rocketlabs.behatide.application.component.startup.wizard;

import de.rocketlabs.behatide.application.manager.modules.ProjectType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import org.controlsfx.dialog.WizardPane;

import java.io.IOException;

public class CreateProjectStep1 extends WizardPane {

    private ListView<ProjectType> projectTypeList;

    public CreateProjectStep1() {
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
}
