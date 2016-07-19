package de.rocketlabs.behatide.application.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MyMenuBar extends VBox
{
    public MenuBar menuBar;

    public MyMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
