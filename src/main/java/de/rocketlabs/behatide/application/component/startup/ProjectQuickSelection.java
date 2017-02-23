package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.action.ActionRunner;
import de.rocketlabs.behatide.application.action.OpenProject;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.manager.project.ProjectManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
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
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                ActionRunner.run(new OpenProject(getSelectionModel().getSelectedItem()));
            }
        });
    }
}
