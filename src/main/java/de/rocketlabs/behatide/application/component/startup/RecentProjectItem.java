package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.action.ActionRunner;
import de.rocketlabs.behatide.application.action.OpenProject;
import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RecentProjectItem extends ListCell<ProjectMetaData> implements FxmlLoading {

    @FXML
    public Label projectPath;
    @FXML
    public Label projectName;
    @FXML
    public Button remove;
    @FXML
    public VBox vBox;

    private AnchorPane content;
    private ListView<ProjectMetaData> view;

    RecentProjectItem(ListView<ProjectMetaData> view) {
        this.view = view;
        content = (AnchorPane) loadFxml();
    }

    @Override
    protected void updateItem(ProjectMetaData item, boolean empty) {
        if (empty) {
            clearContent();
        } else {
            updateContent(item);
        }
    }

    public void openProject() {
        ActionRunner.run(new OpenProject(getItem()));
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        vBox.setPrefWidth(view.getWidth() - 40);
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
        setItem(null);
    }

    private void updateContent(ProjectMetaData item) {
        projectPath.setText(item.getPath());
        projectName.setText(item.getTitle());
        setItem(item);
        setGraphic(content);
    }

    @Override
    public String getFxmlPath() {
        return "/view/startup/RecentProjectItem.fxml";
    }

    @Override
    public Object getRoot() {
        return null;
    }
}
