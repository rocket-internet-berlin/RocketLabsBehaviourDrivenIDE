package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.component.exception.FxmlLoadException;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public interface FxmlLoading<T> {

    default Object getController() {
        return this;
    }

    String getFxmlPath();

    default Object getRoot() {
        return this;
    }

    default T loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getFxmlPath()));
        if (getRoot() != null) {
            fxmlLoader.setRoot(getRoot());
        }
        if (getController() != null) {
            fxmlLoader.setController(getController());
        }
        try {
            //noinspection unchecked
            return (T) fxmlLoader.load();
        } catch (IOException e) {
            throw new FxmlLoadException(e);
        }
    }
}
