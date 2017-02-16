package de.rocketlabs.behatide.application.action;

import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.ProjectLoadedEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowStartup extends FxCapableAction {

    @Override
    public void doAction() {
        executeInFxThreadAndWait(() -> {
            try {
                Stage stage = FXMLLoader.load(getClass().getResource("/view/startup/ProjectSelection.fxml"));
                stage.setTitle("Rocket Labs Behat IDE");
                stage.centerOnScreen();
                stage.show();
                EventManager.addListener(ProjectLoadedEvent.class, new ProjectLoadedListener(stage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private class ProjectLoadedListener implements EventListener<ProjectLoadedEvent> {

        private Stage stage;

        ProjectLoadedListener(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handleEvent(ProjectLoadedEvent event) {
            stage.close();
            EventManager.removeListener(ProjectLoadedEvent.class, this);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }
}
