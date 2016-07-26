package de.rocketlabs.behatide.application.event.listener;

import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import javafx.scene.control.Alert;

public class FileLoadFailedEventListener implements EventListener<FileLoadFailedEvent> {

    @Override
    public void handleEvent(FileLoadFailedEvent event) {
        String message = "Could not load file from " + event.getPath();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        if (event.getError() != null && event.getError().getMessage() != null && !event.getError().getMessage().isEmpty()) {
            alert.setContentText(event.getError().getMessage());
        }
        alert.showAndWait();
    }

    @Override
    public boolean runOnJavaFxThread() {
        return false;
    }
}
