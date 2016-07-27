package de.rocketlabs.behatide.application.event.listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.component.MainScene;
import de.rocketlabs.behatide.application.event.CloseProjectEvent;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.ProjectLoader;
import de.rocketlabs.behatide.modules.behat.BehatModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadProjectListener implements EventListener<LoadProjectEvent> {

    private final ProjectLoader projectLoader;

    public LoadProjectListener() {
        Injector injector = Guice.createInjector(new BehatModule());
        projectLoader = injector.getInstance(ProjectLoader.class);
    }

    @Override
    public void handleEvent(LoadProjectEvent event) {
        ProjectMetaData projectMetaData = event.getProjectMetaData();
        Project project = projectLoader.loadProject(projectMetaData.getPath());

        if (project == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not load project");
            alert.showAndWait();
            EventManager.fireEvent(new CloseProjectEvent(projectMetaData));
            return;
        }

        try {
            openMainWindow(project);
        } catch (IOException e) {
            EventManager.fireEvent(new CloseProjectEvent(projectMetaData));
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }


    private void openMainWindow(Project project) throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/view/IdeApplication.fxml"));
        MainScene root = ((MainScene) stage.getScene().getRoot());
        root.setProject(project);
        stage.setTitle("Rocket Labs Behat IDE");
        stage.show();
        stage.centerOnScreen();
    }
}
