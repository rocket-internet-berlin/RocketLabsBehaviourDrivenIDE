package de.rocketlabs.behatide.application.component.startup;

import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.LoadProjectEvent;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecentProjectItem extends ListCell<ProjectMetaData> {

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
        content = loadFxml();
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
        EventManager.fireEvent(new LoadProjectEvent(getItem()));
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

    private AnchorPane loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/startup/RecentProjectItem.fxml"));
        fxmlLoader.setController(this);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
