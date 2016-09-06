package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.SuiteSelectionChangedEvent;
import de.rocketlabs.behatide.application.component.control.FileTreeItem;
import de.rocketlabs.behatide.application.event.Event;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProjectStructureWidget extends Widget {

    public TreeView<File> treeView;
    private ObjectProperty<Project> project = new SimpleObjectProperty<>();

    public <T extends Event> ProjectStructureWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/widget/ProjectStructureWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        EventManager.addListener(SuiteSelectionChangedEvent.class, this::updateTreeView);
    }

    private void updateTreeView(SuiteSelectionChangedEvent event) {
        List<String> baseFiles = event.getSuite().getBaseFiles();
        String projectFileMask = event.getSuite().getProjectFileMask();

        File parentDirectory = findCommonParent(baseFiles);
        FileTreeItem root = new FileTreeItem(parentDirectory, projectFileMask);
        root.setDirectoryFilter(baseFiles);

        treeView.setRoot(root);
    }

    private File findCommonParent(List<String> baseFiles) {
        return new File("/");
    }

    public Project getProject() {
        return project.get();
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

//    private void updateTreeView() {
//
//        treeView.setRoot(new FileTreeItem(new File(filesBasePath), projectFilesMask));
//    }
}
