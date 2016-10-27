package de.rocketlabs.behatide.application.component.menu;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.component.dialog.AboutDialog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class HelpMenu extends AbstractMenu implements FxmlLoading {

    @FXML
    private ObjectProperty<Scene> scene = new SimpleObjectProperty<>();

    public HelpMenu() {
        super("_Help");
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/menu/HelpMenu.fxml";
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
