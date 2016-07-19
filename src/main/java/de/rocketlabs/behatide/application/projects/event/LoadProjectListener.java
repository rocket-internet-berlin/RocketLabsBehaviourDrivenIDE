package de.rocketlabs.behatide.application.projects.event;

import de.rocketlabs.behatide.application.component.Editor;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.projects.RecentProjectModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadProjectListener implements EventListener<LoadProjectEvent> {

    @Override
    public void handleEvent(LoadProjectEvent event) {
        Stage stage = event.getStage();
        if (stage == null) {
            stage = new Stage();
        }
        try {
            Scene scene = loadProject(event.getProjectModel());
            stage.setScene(scene);
            stage.setTitle("Rocket Labs Behat IDE");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException ignored) {
        }
    }

    private Scene loadProject(RecentProjectModel projectLocation) throws IOException {
        //TODO: Load actual project
        Region root = FXMLLoader.load(getClass().getResource("/view/ideApplication.fxml"));
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        String cssCode = Editor.class.getResource("/css/editor.css").toExternalForm();
        scene.getStylesheets().add(cssCode);
        return scene;
    }
}
