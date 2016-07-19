package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.projects.RecentProjectModel;
import de.rocketlabs.behatide.application.projects.event.LoadProjectEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RecentProjectItem extends ListCell<RecentProjectModel> {

    @FXML
    public Label projectPath;
    @FXML
    public Label projectName;
    @FXML
    public Button remove;
    @FXML
    public VBox vBox;

    private AnchorPane content;
    private ListView<RecentProjectModel> view;

    RecentProjectItem(ListView<RecentProjectModel> view) {
        this.view = view;
        content = loadFxml();
    }

    @Override
    protected void updateItem(RecentProjectModel item, boolean empty) {
        if (empty) {
            clearContent();
        } else {
            updateContent(item);
        }
    }

    public void openProject() {
        EventManager.fireEvent(new LoadProjectEvent(getItem(), ((Stage) getScene().getWindow())));
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

    private void updateContent(RecentProjectModel item) {
        projectPath.setText(item.getPath());
        projectName.setText(item.getTitle());
        setItem(item);
        setGraphic(content);
    }

    private AnchorPane loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/recentProjectItem.fxml"));
        fxmlLoader.setController(this);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
