package de.rocketlabs.behatide.application.component.control;

import de.rocketlabs.behatide.application.util.FileNameComparator;
import de.rocketlabs.behatide.application.util.TreeItemComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TreeItem;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class FileTreeItem extends TreeItem<File> {

    private final String fileMask;
    private final List<String> pathFilters;

    private boolean isFirstTimeChildren = true;
    private Boolean isLeaf;

    public FileTreeItem(File file, @Nullable List<String> pathFilters, @Nullable String fileMask) {
        super(file);
        this.fileMask = fileMask;
        this.pathFilters = pathFilters;
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
        if (isLeaf == null) {
            isLeaf = getValue().isFile();
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
                SortedList<TreeItem<File>> treeItems = new SortedList<>(
                    children,
                    new TreeItemComparator<>(new FileNameComparator())
                );

                for (File childFile : files) {
                    if (isFiltered(childFile)) {
                        continue;
                    }
                    children.add(new FileTreeItem(childFile, pathFilters, fileMask));
                }

                return treeItems;
            }
        }

        return FXCollections.emptyObservableList();
    }

    private boolean isFiltered(File file) {
        if (file.isDirectory()) {
            return isDirectoryFiltered(file);
        }
        return isFileFiltered(file);
    }

    private boolean isFileFiltered(File file) {
        return !(fileMask == null || fileMask.isEmpty()) && !file.getPath().matches(fileMask);
    }

    private boolean isDirectoryFiltered(File file) {
        if (pathFilters == null || pathFilters.size() == 0) {
            return false;
        }
        String absolutePath = file.getAbsolutePath();
        for (String filter : pathFilters) {
            if (absolutePath.contains(filter) || filter.contains(absolutePath)) {
                return false;
            }
        }
        return true;
    }
}
