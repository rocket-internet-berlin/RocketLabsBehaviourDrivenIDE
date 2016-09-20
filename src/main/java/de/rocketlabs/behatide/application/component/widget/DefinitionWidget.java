package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.SuiteSelectionChangedEvent;
import de.rocketlabs.behatide.application.component.control.DefinitionTreeCell;
import de.rocketlabs.behatide.application.component.control.DefinitionTreeItem;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.domain.model.DefinitionContainer;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.List;

public class DefinitionWidget extends Widget {

    public TreeView<Object> treeView;
    private ObjectProperty<Project> project = new SimpleObjectProperty<>();
    private boolean suiteSelected = false;

    public DefinitionWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/widget/DefinitionWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        treeView.setShowRoot(false);
        treeView.setCellFactory(param -> new DefinitionTreeCell(getProject()));
        treeView.setRoot(new TreeItem<>());

        EventManager.addListener(SuiteSelectionChangedEvent.class, new SuiteChangedListener());
    }

    public Project getProject() {
        return project.get();
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    private void initTree(List<DefinitionContainer> definitionContainers) {
        TreeItem<Object> root = treeView.getRoot();
        root.getChildren().clear();
        definitionContainers.forEach(container -> root.getChildren().add(new DefinitionTreeItem(container)));
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    private class SuiteChangedListener implements EventListener<SuiteSelectionChangedEvent> {

        @Override
        public void handleEvent(SuiteSelectionChangedEvent event) {
            if (!event.getProject().equals(getProject())) {
                return;
            }

            if (event.getSuite() == null) {
                suiteSelected = false;
                return;
            }

            suiteSelected = true;

            List<String> definitionContainerIdentifiers = event.getSuite().getDefinitionContainerIdentifiers();
            List<DefinitionContainer> definitions = getProject().getDefinitions(definitionContainerIdentifiers);
            initTree(definitions);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }
}
