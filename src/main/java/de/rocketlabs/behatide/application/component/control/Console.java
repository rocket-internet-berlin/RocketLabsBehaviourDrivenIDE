package de.rocketlabs.behatide.application.component.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class Console extends AnchorPane {

    @FXML
    private TextArea textArea;

    public Console() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/control/IoConsoleTab.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void write(String text) {
        textArea.appendText(text);
    }

    public String read() {
        throw new NotImplementedException();
    }
}
