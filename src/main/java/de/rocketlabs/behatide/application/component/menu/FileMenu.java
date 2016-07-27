package de.rocketlabs.behatide.application.component.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;

import java.io.IOException;

public class FileMenu extends Menu {

    public FileMenu() {
        super("File");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/menu/FileMenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void openAction() {
    }

    @FXML
    private void saveAction() {
    }

    @FXML
    private void settingsAction() {
    }

    @FXML
    private void exitAction() {
        int i = 0;
    }
}
