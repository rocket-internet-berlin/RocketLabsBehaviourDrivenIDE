package de.rocketlabs.behatide.application;

import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.event.ShowStartupEvent;
import de.rocketlabs.behatide.application.event.listener.FileLoadFailedEventListener;
import de.rocketlabs.behatide.application.event.listener.LoadProjectListener;
import de.rocketlabs.behatide.application.event.listener.ShowStartupEventListener;
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
            EventManager.fireEvent(new ShowStartupEvent());
        } else {
            openProjects.forEach(project -> EventManager.fireEvent(new LoadProjectEvent(project)));
        }
    }

    private void registerEventListeners() {
        EventManager.addListener(LoadProjectEvent.class, new LoadProjectListener());
        EventManager.addListener(FileLoadFailedEvent.class, new FileLoadFailedEventListener());
        EventManager.addListener(ShowStartupEvent.class, new ShowStartupEventListener());
    }

}
