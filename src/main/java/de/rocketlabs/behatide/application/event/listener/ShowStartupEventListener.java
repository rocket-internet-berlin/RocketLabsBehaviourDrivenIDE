package de.rocketlabs.behatide.application.event.listener;

import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.event.ShowStartupEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ShowStartupEventListener implements EventListener<ShowStartupEvent> {

    @Override
    public void handleEvent(ShowStartupEvent event) {
        try {
            Stage stage = FXMLLoader.load(getClass().getResource("/view/startup/ProjectSelection.fxml"));
            stage.setTitle("Rocket Labs Behat IDE");
            stage.centerOnScreen();
            stage.show();
            EventManager.addListener(LoadProjectEvent.class, new LoadProjectListener(stage));
        } catch (Exception ignored) {
            throw new RuntimeException(ignored);
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }

    private class LoadProjectListener implements EventListener<LoadProjectEvent> {

        private Stage stage;

        LoadProjectListener(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handleEvent(LoadProjectEvent event) {
            stage.close();
            EventManager.removeListener(LoadProjectEvent.class, this);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }

}
