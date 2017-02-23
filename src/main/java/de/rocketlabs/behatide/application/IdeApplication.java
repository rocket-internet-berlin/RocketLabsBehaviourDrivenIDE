package de.rocketlabs.behatide.application;

import de.rocketlabs.behatide.application.action.ActionRunner;
import de.rocketlabs.behatide.application.action.ShowStartup;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.application.event.listener.FileLoadFailedEventListener;
import de.rocketlabs.behatide.application.manager.project.ProjectManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

import java.io.IOException;
import java.util.List;

public class IdeApplication {

    public void initializeStage() throws IOException {
        StateStorageManager storageManager = StateStorageManager.getInstance();

        ProjectManager projectManager = storageManager.loadState(ProjectManager.class);
        List<ProjectMetaData> openProjects = projectManager.getOpenProjects();

        registerEventListeners();

        if (openProjects.isEmpty()) {
            ActionRunner.run(new ShowStartup());
        } else {
            openProjects.forEach(project -> ActionRunner.run(new de.rocketlabs.behatide.application.action.OpenProject(project)));
        }
    }

    private void registerEventListeners() {
        EventManager.addListener(FileLoadFailedEvent.class, new FileLoadFailedEventListener());
    }
}
