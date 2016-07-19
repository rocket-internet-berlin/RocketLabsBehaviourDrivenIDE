package de.rocketlabs.behatide.application;

import de.rocketlabs.behatide.application.component.Editor;
import de.rocketlabs.behatide.application.configuration.components.RecentProjectsManager;
import de.rocketlabs.behatide.application.configuration.components.storage.StateStorageManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class IdeApplication {

    public void initializeStage(Stage stage) throws IOException {
        StateStorageManager storageManager = StateStorageManager.getInstance();

        RecentProjectsManager recentProjectsManager = storageManager.loadState(RecentProjectsManager.class);
        String openProject = recentProjectsManager.getOpenProject();

        Scene scene;
        if (openProject != null) {
            scene = loadProjectSelection();
        } else {
            scene = loadProject(openProject);
        }

        stage.setScene(scene);
        stage.setTitle("Rocket Labs Behat IDE");
        stage.show();
        stage.centerOnScreen();
    }

    private Scene loadProjectSelection() throws IOException {
        Region root = FXMLLoader.load(getClass().getResource("/view/projectSelection.fxml"));
        return new Scene(root);
    }

    private Scene loadProject(String projectLocation) throws IOException {
        Region root = FXMLLoader.load(getClass().getResource("/view/ideApplication.fxml"));
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        String cssCode = Editor.class.getResource("/css/editor.css").toExternalForm();
        scene.getStylesheets().add(cssCode);
        return scene;
    }
}
