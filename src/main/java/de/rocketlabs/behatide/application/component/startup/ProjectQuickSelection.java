package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.configuration.storage.StateStorageManager;
import de.rocketlabs.behatide.application.manager.project.ProjectManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectQuickSelection extends ListView<ProjectMetaData> {

    public ProjectQuickSelection() {
        setCellFactory(RecentProjectItem::new);
        populateView();
    }

    @Override
    protected double computePrefWidth(double height) {
        if (getItems().isEmpty()) {
            return 0;
        }
        return 300;
    }


    private void populateView() {
        StateStorageManager storageManager = StateStorageManager.getInstance();
        ProjectManager projectManager = storageManager.loadState(ProjectManager.class);
        @NotNull List<ProjectMetaData> recentProjects = projectManager.getRecentProjects();
        setVisible(!recentProjects.isEmpty());
        setItems(FXCollections.observableList(recentProjects));
    }


}
