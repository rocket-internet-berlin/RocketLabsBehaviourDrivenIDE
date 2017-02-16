package de.rocketlabs.behatide.application.component.control;

import de.rocketlabs.behatide.application.action.ActionRunner;
import de.rocketlabs.behatide.application.editor.action.OpenFile;
import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.scene.control.TreeCell;

import java.io.File;

public class FileTreeCell extends TreeCell<File> {

    public FileTreeCell(ProjectContext projectContext) {
        setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                ActionRunner.run(new OpenFile(getItem(), projectContext));
            }
        });
    }

    @Override
    protected void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }
}
