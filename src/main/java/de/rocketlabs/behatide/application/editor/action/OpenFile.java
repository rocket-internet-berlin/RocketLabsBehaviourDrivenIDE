package de.rocketlabs.behatide.application.editor.action;

import de.rocketlabs.behatide.application.action.FxCapableAction;
import de.rocketlabs.behatide.application.editor.component.Editor;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.application.model.ProjectContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenFile extends FxCapableAction {

    private final File file;
    private final ProjectContext context;

    public OpenFile(File file, ProjectContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    public void doAction() {
        executeInFxThreadAndWait(() -> {
            try {
                Path path = file.toPath();
                Editor editor = context.getEditor();

                editor.replaceText(String.join("\n", Files.readAllLines(path)));
                editor.setOpenFilePath(path);
            } catch (IOException e) {
                EventManager.fireEvent(new FileLoadFailedEvent(file));
            }
        });
    }
}
