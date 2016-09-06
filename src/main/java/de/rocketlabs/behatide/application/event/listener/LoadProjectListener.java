package de.rocketlabs.behatide.application.event.listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.component.MainScene;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.event.CloseProjectEvent;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.modules.AbstractModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadProjectListener implements EventListener<LoadProjectEvent> {

    private final StateStorageManager storageManager;
    private final ModuleManager moduleManager;

    public LoadProjectListener() {
        storageManager = StateStorageManager.getInstance();
        moduleManager = ModuleManager.getInstance();
    }

    @Override
    public void handleEvent(LoadProjectEvent event) {
        ProjectMetaData projectMetaData = event.getProjectMetaData();
        AbstractModule module = moduleManager.forName(projectMetaData.getModuleName());
        Injector injector = Guice.createInjector(module);
        Class<? extends Project> projectClass = injector.getInstance(Project.class).getClass();

        Map<StorageParameter, String> parameters = new HashMap<StorageParameter, String>() {{
            put(StorageParameter.STORAGE_DIRECTORY, projectMetaData.getPath());
        }};
        Project project = storageManager.loadState(projectClass, parameters);

        if (project == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not load project");
            alert.showAndWait();
            EventManager.fireEvent(new CloseProjectEvent(projectMetaData));
            return;
        }

        try {
            openMainWindow(project);
        } catch (IOException e) {
            EventManager.fireEvent(new CloseProjectEvent(projectMetaData));
            //TODO: Improve error handling here
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }

    private void openMainWindow(Project project) throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/view/IdeApplication.fxml"));
        Scene scene = stage.getScene();
        MainScene root = ((MainScene) scene.getRoot());
        root.setProject(project);
        stage.setTitle(project.getTitle() + " - Rocket Labs Behat IDE");
        stage.show();
        stage.centerOnScreen();
    }
}
