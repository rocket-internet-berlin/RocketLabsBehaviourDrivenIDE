package de.rocketlabs.behatide.application.util;

import javafx.scene.control.TreeItem;

import java.util.Comparator;

public class TreeItemComparator<T> implements Comparator<TreeItem<T>> {

    private Comparator<T> valueComparator;

    public TreeItemComparator(Comparator<T> valueComparator) {
        this.valueComparator = valueComparator;
    }

    @Override
    public int compare(TreeItem<T> o1, TreeItem<T> o2) {
        return valueComparator.compare(o1.getValue(), o2.getValue());
    }
}
