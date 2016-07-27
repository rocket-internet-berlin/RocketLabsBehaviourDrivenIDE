package de.rocketlabs.behatide.application.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class MainMenuBar extends MenuBar
{

    public MainMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
