package de.rocketlabs.behatide.application.component.control;

import de.rocketlabs.behatide.application.util.Collectors;
import de.rocketlabs.behatide.domain.model.Definition;
import de.rocketlabs.behatide.domain.model.DefinitionContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DefinitionTreeItem extends TreeItem<Object> {

    private boolean isFirstTimeChildren = true;

    public DefinitionTreeItem(DefinitionContainer container) {
        super(container);
    }

    private DefinitionTreeItem(Definition definition) {
        super(definition);
    }

    @Override
    public boolean isLeaf() {
        return getValue() instanceof Definition;
    }

    @Override
    public ObservableList<TreeItem<Object>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

            super.getChildren().setAll(buildChildren());
        }
        return super.getChildren();
    }

    private ObservableList<TreeItem<Object>> buildChildren() {
        if (getValue() instanceof Definition) {
            return FXCollections.emptyObservableList();
        }

        return ((DefinitionContainer) getValue()).getDefinitions()
            .stream()
            .map(DefinitionTreeItem::new)
            .collect(Collectors.toObservableList());
    }
}
