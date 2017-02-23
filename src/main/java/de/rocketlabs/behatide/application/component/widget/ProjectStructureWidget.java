package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import de.rocketlabs.behatide.application.component.SuiteSelectionChangedEvent;
import de.rocketlabs.behatide.application.component.control.FileTreeCell;
import de.rocketlabs.behatide.application.component.control.FileTreeItem;
import de.rocketlabs.behatide.application.editor.event.ProfileSelectionChangedEvent;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.application.util.FileUtils;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.List;

public class ProjectStructureWidget extends Widget implements FxmlLoading {

    public TreeView<File> treeView;
    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();
    private boolean suiteSelected = false;

    public ProjectStructureWidget() {
        loadFxml();

        EventManager.addListener(SuiteSelectionChangedEvent.class, new SuiteChangedListener());
        EventManager.addListener(ProfileSelectionChangedEvent.class, new ProfileChangedListener());
    }

    @Override
    public String getFxmlPath() {
        return "/view/widget/ProjectStructureWidget.fxml";
    }

    private void initTree(List<String> paths, String fileMask) {
        Project project = getProjectContext().getProject();
        project.getPathReplacements().forEach(
            (pattern, replacement) -> paths.replaceAll(path -> path.replace(pattern, replacement)));
        File parentDirectory = FileUtils.findCommonParent(paths);
        FileTreeItem root = new FileTreeItem(parentDirectory, paths, fileMask);

        treeView.setCellFactory(param -> new FileTreeCell(getProjectContext()));
        treeView.setRoot(root);
    }

    @FXML
    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    @FXML
    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    @FXML
    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
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

            List<String> paths = event.getSuite().getPaths();
            String fileMask = event.getProjectContext().getProject().getFileMask();

            initTree(paths, fileMask);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }

    private class ProfileChangedListener implements EventListener<ProfileSelectionChangedEvent> {

        @Override
        public void handleEvent(ProfileSelectionChangedEvent event) {
            if (!event.getProjectContext().equals(getProjectContext()) || suiteSelected || event.getProfile() == null) {
                return;
            }

            List<String> paths = event.getProfile().getPaths();
            String fileMask = event.getProjectContext().getProject().getFileMask();

            initTree(paths, fileMask);
        }

        @Override
        public boolean runOnJavaFxThread() {
            return true;
        }
    }
}
