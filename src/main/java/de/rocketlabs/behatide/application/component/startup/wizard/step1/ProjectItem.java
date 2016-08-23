package de.rocketlabs.behatide.application.component.startup.wizard.step1;

import de.rocketlabs.behatide.domain.model.ProjectType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ProjectItem extends ListCell<ProjectType> {

    private ListView<ProjectType> view;

    @FXML
    public ImageView icon;
    @FXML
    public Label title;
    @FXML
    private Node content;

    public ProjectItem(ListView<ProjectType> view) {
        this.view = view;
        content = loadFxml();
    }

    private Node loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/view/startup/createProjectWizard/step1/ProjectItem.fxml"));
        fxmlLoader.setController(this);
        try {
            Node load = fxmlLoader.load();
            setOnMouseClicked(e -> view.getSelectionModel().select(getItem()));
            return load;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void updateItem(ProjectType item, boolean empty) {
        if (empty) {
            clearContent();
        } else {
            updateContent(item);
        }
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
        setItem(null);
    }

    private void updateContent(ProjectType item) {
        icon.setImage(item.getIcon());
        title.setText(item.getTypeName());
        setItem(item);
        setGraphic(content);
    }
}
