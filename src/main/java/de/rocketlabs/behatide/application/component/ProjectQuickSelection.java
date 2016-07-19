package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.configuration.components.storage.StateStorageManager;
import de.rocketlabs.behatide.application.projects.RecentProjectModel;
import de.rocketlabs.behatide.application.projects.RecentProjectsManager;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectQuickSelection extends ListView<RecentProjectModel> {

    public ProjectQuickSelection() {
        populateView();
        setCellFactory(RecentProjectItem::new);
    }

    private void populateView() {
        StateStorageManager storageManager = StateStorageManager.getInstance();
        RecentProjectsManager recentProjectsManager = storageManager.loadState(RecentProjectsManager.class);
        @NotNull List<RecentProjectModel> recentProjects = recentProjectsManager.getRecentProjects();

        setItems(FXCollections.observableList(recentProjects));
    }
}
