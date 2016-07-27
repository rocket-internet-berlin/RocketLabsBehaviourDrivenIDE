package de.rocketlabs.behatide.application.manager.project;

import de.rocketlabs.behatide.application.configuration.storage.State;
import de.rocketlabs.behatide.application.configuration.storage.StateStorageManager;
import de.rocketlabs.behatide.application.event.*;
import de.rocketlabs.behatide.application.event.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@State(name = "ModuleManager")
public class ProjectManager {

    private static final int RECENT_PROJECTS_LIMIT = 5;
    private List<ProjectMetaData> recentProjects = new LinkedList<>();
    private Set<ProjectMetaData> openProjects = new HashSet<>();

    public ProjectManager() {
        EventManager.addListener(LoadProjectEvent.class, new LoadProjectListener());
        EventManager.addListener(CloseProjectEvent.class, new CloseProjectListener());
        EventManager.addListener(FileLoadFailedEvent.class, new FileLoadFailedListener());
    }

    @NotNull
    public List<ProjectMetaData> getRecentProjects() {
        return recentProjects
            .stream()
            .map(ProjectMetaData::clone)
            .collect(Collectors.toList());
    }

    private void addRecentProject(ProjectMetaData recentProject) {
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

    private class LoadProjectListener implements EventListener<LoadProjectEvent> {

        @Override
        public void handleEvent(LoadProjectEvent event) {
            addRecentProject(event.getProjectMetaData());
            openProjects.add(event.getProjectMetaData());

        }

        @Override
        public boolean runOnJavaFxThread() {
            return false;
        }
    }

    private class CloseProjectListener implements EventListener<CloseProjectEvent> {

        @Override
        public void handleEvent(CloseProjectEvent event) {
            openProjects.remove(event.getProjectModel());
        }

        @Override
        public boolean runOnJavaFxThread() {
            return false;
        }
    }

    private class FileLoadFailedListener implements EventListener<FileLoadFailedEvent> {

        @Override
        public void handleEvent(FileLoadFailedEvent event) {
            removeRecentProject(event.getPath());
        }

        @Override
        public boolean runOnJavaFxThread() {
            return false;
        }
    }

}
