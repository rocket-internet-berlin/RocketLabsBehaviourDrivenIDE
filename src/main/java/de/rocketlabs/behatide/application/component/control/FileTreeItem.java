package de.rocketlabs.behatide.application.component.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.List;

public class FileTreeItem extends TreeItem<File> {

    private final String fileMask;

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;
    private List<String> directoryFilter;

    public FileTreeItem(File file) {
        this(file, ".*");
    }

    public FileTreeItem(File file, String fileMask) {
        super(file);
        this.fileMask = fileMask;
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            File f = getValue();
            isLeaf = f.isFile();
        }

        return isLeaf;
    }

    /**
     * Returning a collection of type ObservableList containing TreeItems, which
     * represent all children available in handed TreeItem.
     *
     * @param TreeItem the root node from which children a collection of TreeItem
     *                 should be created.
     * @return an ObservableList<TreeItem<File>> containing TreeItems, which
     * represent all children available in handed TreeItem. If the
     * handed TreeItem is a leaf, an empty list is returned.
     */
    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File file = TreeItem.getValue();
        if (file != null && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files) {
                    children.add(new FileTreeItem(childFile));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }

    public void setDirectoryFilter(List<String> directoryFilter) {
        this.directoryFilter = directoryFilter;
    }
}
