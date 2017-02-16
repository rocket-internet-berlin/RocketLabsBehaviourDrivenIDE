package de.rocketlabs.behatide.application.action;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.component.EditorView;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.ProjectLoadedEvent;
import de.rocketlabs.behatide.application.manager.modules.ModuleManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.modules.AbstractModule;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class OpenProject extends FxCapableAction {

    private final StateStorageManager storageManager;
    private final ModuleManager moduleManager;
    private final ProjectMetaData metaData;

    public OpenProject(ProjectMetaData metaData) {
        this.metaData = metaData;
        storageManager = StateStorageManager.getInstance();
        moduleManager = ModuleManager.getInstance();
    }

    public void doAction() {
        AbstractModule module = moduleManager.forName(metaData.getModuleName());
        Injector injector = Guice.createInjector(module);
        Class<? extends Project> projectClass = injector.getInstance(Project.class).getClass();

        Map<StorageParameter, String> parameters = new HashMap<StorageParameter, String>() {{
            put(StorageParameter.STORAGE_DIRECTORY, metaData.getPath());
        }};
        Project project = storageManager.loadState(projectClass, parameters);

        executeInFxThread(() -> openMainWindow(project));
        EventManager.fireEvent(new ProjectLoadedEvent(metaData));
    }

    private void openMainWindow(Project project) {
        EditorView view = new EditorView();
        ProjectContext context = new ProjectContext(project, view.getEditor());
        view.setProjectContext(context);

        Scene scene = new Scene(view);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(project.getTitle() + " - Rocket Labs Behat IDE");
        stage.show();
        stage.centerOnScreen();
    }
}
