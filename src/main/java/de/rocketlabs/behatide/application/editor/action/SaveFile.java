package de.rocketlabs.behatide.application.editor.action;

import de.rocketlabs.behatide.application.action.Action;
import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveFile implements Action {

    private ProjectContext projectContext;

    public SaveFile(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }

    @Override
    public void doAction() {
        Path openFilePath = projectContext.getEditor().getOpenFilePath();
        String content = projectContext.getEditor().getText();

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
