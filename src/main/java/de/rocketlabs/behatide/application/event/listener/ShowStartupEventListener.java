package de.rocketlabs.behatide.application.event.listener;

import de.rocketlabs.behatide.application.event.EventListener;
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
        } catch (Exception ignored) {
            throw new RuntimeException(ignored);
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }

}
