package de.rocketlabs.behatide.modules.behat.runner;

import de.rocketlabs.behatide.modules.behat.model.BehatProfile;
import de.rocketlabs.behatide.modules.behat.model.BehatSuite;
import de.rocketlabs.behatide.modules.behat.model.Project;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class TestRunner implements de.rocketlabs.behatide.domain.runner.TestRunner<Project, BehatProfile, BehatSuite> {

    @Override
    public void runFile(Project project, BehatProfile profile, BehatSuite suite, Path filePath) {
        String behatExecutablePath = project.getBehatExecutablePath();

        ProcessBuilder processBuilder = new ProcessBuilder(
                behatExecutablePath,
                "-c", project.getBehatConfigurationFile(),
                "-p", profile.getName(),
                "-s", suite.getName(),
                filePath.toString()
        );
        try {
            //TODO: Prettify (hack hack hack)
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            Stage stage = new Stage();
            HBox root = new HBox();
            TextArea e = new TextArea();
            e.setPrefWidth(1000);
            e.editableProperty().setValue(false);
            root.getChildren().add(e);
            Scene value = new Scene(root, 1000, 300);
            stage.setScene(value);
            stage.show();

            printStream(inputStream, e);
            printStream(errorStream, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStream(InputStream inputStream, TextArea e) {
        new Thread(
                () -> {
                    try {
                        BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
                        String s;
                        while ((s = buf.readLine()) != null) {
                            e.appendText(s + '\n');
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }).start();
    }
}
