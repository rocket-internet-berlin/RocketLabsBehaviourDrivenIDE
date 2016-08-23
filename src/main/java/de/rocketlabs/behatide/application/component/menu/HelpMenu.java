package de.rocketlabs.behatide.application.component.menu;

import de.rocketlabs.behatide.application.component.dialog.AboutDialog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;

import java.io.IOException;

public class HelpMenu extends Menu {

    @FXML
    private ObjectProperty<Scene> scene = new SimpleObjectProperty<>();

    public HelpMenu() {
        super("_Help");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/menu/HelpMenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Scene getScene() {
        return scene.get();
    }

    public void setScene(Scene scene) {
        this.scene.set(scene);
    }

    @FXML
    public ObjectProperty<Scene> sceneProperty() {
        return scene;
    }

    @FXML
    private void showAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(scene.get().getWindow());
    }
}
