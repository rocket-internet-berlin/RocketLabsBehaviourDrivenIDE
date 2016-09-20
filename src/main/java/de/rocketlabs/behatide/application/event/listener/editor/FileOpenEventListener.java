package de.rocketlabs.behatide.application.event.listener.editor;

import de.rocketlabs.behatide.application.component.editor.Editor;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileLoadFailedEvent;
import de.rocketlabs.behatide.application.event.FileOpenEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOpenEventListener implements EventListener<FileOpenEvent> {

    private Editor editor;

    public FileOpenEventListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void handleEvent(FileOpenEvent e) {
        if (e.getProject() == editor.getProject() && e.getItem().isFile()) {
            try {
                Path path = e.getItem().toPath();
                editor.replaceText(String.join("\n", Files.readAllLines(path)));
                editor.setOpenFilePath(path);
            } catch (IOException e1) {
                EventManager.fireEvent(new FileLoadFailedEvent(e.getItem()));
            }
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }
}
