package de.rocketlabs.behatide.application.event.listener.editor;

import de.rocketlabs.behatide.application.component.editor.Editor;
import de.rocketlabs.behatide.application.event.EventListener;
import de.rocketlabs.behatide.application.event.FileSaveRequestEvent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveFileListener implements EventListener<FileSaveRequestEvent> {

    private Editor editor;

    public SaveFileListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void handleEvent(FileSaveRequestEvent event) {
        if (!event.getProject().equals(editor.getProject()) || editor.getOpenFilePath() == null) {
            return;
        }

        Path openFilePath = editor.getOpenFilePath();
        String content = editor.getText();

        try {
            Files.write(
                openFilePath,
                content.getBytes(),
                StandardOpenOption.DSYNC,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace(System.err);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Could not save file " + openFilePath.toAbsolutePath());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
