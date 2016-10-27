package de.rocketlabs.behatide.application.component.startup.wizard.step1;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.domain.model.ProjectType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProjectItem extends ListCell<ProjectType> implements FxmlLoading<HBox> {

    private ListView<ProjectType> view;

    @FXML
    public ImageView icon;
    @FXML
    public Label title;
    @FXML
    private HBox content;

    public ProjectItem(ListView<ProjectType> view) {
        this.view = view;
        content = loadFxml();
        setOnMouseClicked(e -> view.getSelectionModel().select(getItem()));
    }

    @Override
    public String getFxmlPath() {
        return "/view/startup/createProjectWizard/step1/ProjectItem.fxml";
    }

    @Override
    public Object getRoot() {
        return null;
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
