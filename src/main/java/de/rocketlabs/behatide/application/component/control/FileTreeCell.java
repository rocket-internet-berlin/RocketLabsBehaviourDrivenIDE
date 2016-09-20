package de.rocketlabs.behatide.application.component.control;

import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileOpenEvent;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.scene.control.TreeCell;

import java.io.File;

public class FileTreeCell extends TreeCell<File> {

    public FileTreeCell(Project project) {
        setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                EventManager.fireEvent(new FileOpenEvent(getItem(), project));
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
