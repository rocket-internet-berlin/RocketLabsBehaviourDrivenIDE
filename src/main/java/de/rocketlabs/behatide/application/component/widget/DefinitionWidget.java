package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.component.SuiteSelectionChangedEvent;
import de.rocketlabs.behatide.application.component.control.DefinitionTreeCell;
import de.rocketlabs.behatide.application.component.control.DefinitionTreeItem;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.DefinitionContainer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class DefinitionWidget extends Widget implements FxmlLoading {

    public TreeView<Object> treeView;
    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();
    private boolean suiteSelected = false;

    public DefinitionWidget() {
        loadFxml();

        treeView.setShowRoot(false);
        treeView.setCellFactory(param -> new DefinitionTreeCell(getProjectContext()));
        treeView.setRoot(new TreeItem<>());

        EventManager.addListener(SuiteSelectionChangedEvent.class, new SuiteChangedListener());
    }

    @Override
    public String getFxmlPath() {
        return "/view/widget/DefinitionWidget.fxml";
    }

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
    }

    private void initTree(List<DefinitionContainer> definitionContainers) {
        TreeItem<Object> root = treeView.getRoot();
        root.getChildren().clear();
        definitionContainers.forEach(container -> root.getChildren().add(new DefinitionTreeItem(container)));
    }

    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    private class SuiteChangedListener implements EventListener<SuiteSelectionChangedEvent> {

        @Override
        public void handleEvent(SuiteSelectionChangedEvent event) {
            if (!event.getProjectContext().equals(getProjectContext())) {
                return;
            }

            if (event.getSuite() == null) {
                suiteSelected = false;
                return;
            }

            suiteSelected = true;

            List<String> definitionContainerIdentifiers = event.getSuite().getDefinitionContainerIdentifiers();
            List<DefinitionContainer> definitions = getProjectContext().getProject().getDefinitions(definitionContainerIdentifiers);
            initTree(definitions);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }
}
