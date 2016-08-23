package de.rocketlabs.behatide.application.component.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Popup;

import java.io.IOException;

public class AboutDialog extends Popup {

    @FXML
    private Label version;

    public AboutDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dialog/AboutDialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        opacityProperty().setValue(1);
        setAutoHide(true);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
}
