package de.rocketlabs.behatide.application;

import de.rocketlabs.behatide.application.configuration.components.storage.StateStorageManager;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.projects.RecentProjectModel;
import de.rocketlabs.behatide.application.projects.RecentProjectsManager;
import de.rocketlabs.behatide.application.projects.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.projects.event.LoadProjectListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class IdeApplication {

    public void initializeStage(Stage stage) throws IOException {
        StateStorageManager storageManager = StateStorageManager.getInstance();

        RecentProjectsManager recentProjectsManager = storageManager.loadState(RecentProjectsManager.class);
        RecentProjectModel openProject = recentProjectsManager.getOpenProject();

        Scene scene;
        EventManager.addListener(LoadProjectEvent.class, new LoadProjectListener());
        if (openProject == null) {
            scene = loadProjectSelection();
            stage.setScene(scene);
            stage.setTitle("Rocket Labs Behat IDE");
            stage.show();
            stage.centerOnScreen();
        } else {
            EventManager.fireEvent(new LoadProjectEvent(openProject, stage));
        }
    }

    private Scene loadProjectSelection() throws IOException {
        Region root = FXMLLoader.load(getClass().getResource("/view/projectSelection.fxml"));
        return new Scene(root, root.getPrefWidth(), root.getPrefHeight());
    }
}
