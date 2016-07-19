package de.rocketlabs.behatide.application.projects;

import de.rocketlabs.behatide.application.configuration.components.storage.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@State(name = "RecentProjectManager")
public class RecentProjectsManager {

    private static final int RECENT_PROJECTS_LIMIT = 5;
    private List<RecentProjectModel> recentProjects = new LinkedList<>();
    private RecentProjectModel openProject;

    @NotNull
    public List<RecentProjectModel> getRecentProjects() {
        return recentProjects
                .stream()
                .map(RecentProjectModel::clone)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void setRecentProject(@NotNull RecentProjectModel recentProject) {
        if (recentProjects.contains(recentProject)) {
            recentProjects.remove(recentProject);
        }
        recentProjects.add(0, recentProject);
        if (recentProjects.size() > RECENT_PROJECTS_LIMIT) {
            recentProjects.remove(RECENT_PROJECTS_LIMIT);
        }
    }

    public @Nullable RecentProjectModel getOpenProject() {
        return openProject;
    }

    public void setOpenProject(@Nullable RecentProjectModel openProject) {
        this.openProject = openProject;
    }
}
