package de.rocketlabs.behatide.application.component.dialog;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Popup;

public class AboutDialog extends Popup implements FxmlLoading {

    @FXML
    private Label version;

    public AboutDialog() {
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/dialog/AboutDialog.fxml";
    }

    private String getVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        if (version == null) {
            return "DEVELOPMENT VERSION";
        }
        return version;
    }

    @FXML
    private void initialize() {
        version.setText(getVersion());
    }
}
