package de.rocketlabs.behatide.application.component.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;

import java.io.IOException;

public class HelpMenu extends Menu {

    public HelpMenu() {
        super("Help");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/menu/HelpMenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        int i = 0;
    }
}
