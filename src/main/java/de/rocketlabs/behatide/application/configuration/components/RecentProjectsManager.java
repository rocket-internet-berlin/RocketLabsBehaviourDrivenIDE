package de.rocketlabs.behatide.application.configuration.components;

import de.rocketlabs.behatide.application.configuration.components.storage.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@State(name = "RecentProjectManager")
public class RecentProjectsManager {

    private static final int RECENT_PROJECTS_LIMIT = 5;
    private List<String> recentProjects = new LinkedList<>();
    private String openProject;

    @NotNull
    public List<String> getRecentProjects() {
        List<String> copy = new LinkedList<>();
        Collections.copy(copy, recentProjects);
        return copy;
    }

    public void setRecentProject(@NotNull String recentProject) {
        if (recentProjects.contains(recentProject)) {
            recentProjects.remove(recentProject);
        }
        recentProjects.add(0, recentProject);
        if (recentProjects.size() > RECENT_PROJECTS_LIMIT) {
            recentProjects.remove(RECENT_PROJECTS_LIMIT);
        }
    }

    @Nullable
    public String getOpenProject() {
        return openProject;
    }

    public void setOpenProject(@Nullable String openProject) {
        this.openProject = openProject;
    }
}
