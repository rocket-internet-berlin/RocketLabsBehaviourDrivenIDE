package de.rocketlabs.behatide;

import de.rocketlabs.behatide.application.IdeApplication;
import de.rocketlabs.behatide.application.configuration.components.storage.StateStorageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        IdeApplication application = new IdeApplication();
        application.initializeStage(stage);
    }

    @Override
    public void stop() throws Exception {
        StateStorageManager.getInstance().save();
    }
}
