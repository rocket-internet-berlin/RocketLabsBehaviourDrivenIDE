package de.rocketlabs.behatide.application.component.menu;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileSaveRequestEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class FileMenu extends AbstractMenu implements FxmlLoading {

    @FXML
    private ObjectProperty<Scene> scene = new SimpleObjectProperty<>();

    public FileMenu() {
        super("_File");
        loadFxml();
    }

    @Override
    public String getFxmlPath() {
        return "/view/menu/FileMenu.fxml";
    }

    @FXML
    private void saveAction() {
        EventManager.fireEvent(new FileSaveRequestEvent(getProject()));
    }

    @FXML
    private void exitAction() {
        Window window = getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
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
}
