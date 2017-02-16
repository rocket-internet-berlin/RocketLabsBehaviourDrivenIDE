package de.rocketlabs.behatide.application.manager.project;

import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.application.event.ProjectClosedEvent;
import de.rocketlabs.behatide.application.event.ProjectLoadedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@State(name = "ProjectManager")
public class ProjectManager {

    private static final int RECENT_PROJECTS_LIMIT = 5;
    private List<ProjectMetaData> recentProjects = new LinkedList<>();
    private Set<ProjectMetaData> openProjects = new HashSet<>();

    public ProjectManager() {
        EventManager.addListener(ProjectLoadedEvent.class, this::handleLoadProjectEvent);
        EventManager.addListener(ProjectClosedEvent.class, this::handleProjectClosedEvent);
        EventManager.addListener(FileLoadFailedEvent.class, this::handleFileLoadFailedEvent);
    }

    @NotNull
    public List<ProjectMetaData> getRecentProjects() {
        return recentProjects
            .stream()
            .map(ProjectMetaData::clone)
            .collect(Collectors.toList());
    }

    private void addRecentProject(ProjectMetaData recentProject) {
        if (recentProject == null) {
            throw new IllegalArgumentException("recentProject may not be null");
        }
        recentProjects.remove(recentProject);
        recentProjects.add(0, recentProject);
        if (recentProjects.size() > RECENT_PROJECTS_LIMIT) {
            recentProjects.remove(RECENT_PROJECTS_LIMIT);
        }
        StateStorageManager.getInstance().setState(this);
    }

    private void removeRecentProject(String filePath) {
        for (Iterator<ProjectMetaData> iterator = recentProjects.iterator(); iterator.hasNext(); ) {
            if (Objects.equals(iterator.next().getPath(), filePath)) {
                iterator.remove();
                StateStorageManager.getInstance().setState(this);
            }
        }
    }

    public List<ProjectMetaData> getOpenProjects() {
        return openProjects
            .stream()
            .map(ProjectMetaData::clone)
            .collect(Collectors.toList());
    }

    private void handleLoadProjectEvent(ProjectLoadedEvent event) {
        addRecentProject(event.getProjectMetaData());
        openProjects.add(event.getProjectMetaData());
        StateStorageManager.getInstance().setState(ProjectManager.this);
    }

    private void handleProjectClosedEvent(ProjectClosedEvent event) {
        openProjects.remove(event.getProjectModel());
        StateStorageManager.getInstance().setState(ProjectManager.this);
    }

    private void handleFileLoadFailedEvent(FileLoadFailedEvent event) {
        removeRecentProject(event.getPath());
        StateStorageManager.getInstance().setState(ProjectManager.this);
    }
}
