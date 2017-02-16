package de.rocketlabs.behatide.modules.behat.runner;

import de.rocketlabs.behatide.application.component.control.Console;
import de.rocketlabs.behatide.modules.behat.model.BehatProfile;
import de.rocketlabs.behatide.modules.behat.model.BehatSuite;
import de.rocketlabs.behatide.modules.behat.model.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class TestRunner implements de.rocketlabs.behatide.domain.runner.TestRunner<Project, BehatProfile, BehatSuite> {

    @Override
    public void runFile(Project project,
                        BehatProfile profile,
                        BehatSuite suite,
                        Path filePath,
                        Console console) {
        String behatExecutablePath = project.getBehatExecutablePath();

        ProcessBuilder processBuilder = new ProcessBuilder(
            behatExecutablePath,
            "-c", project.getBehatConfigurationFile(),
            "-p", profile.getName(),
            "-s", suite.getName(),
            filePath.toString()
        );
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            printStream(inputStream, console);
            printStream(errorStream, console);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStream(InputStream inputStream, Console console) {
        new Thread(
            () -> {
                try {
                    BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
                    String s;
                    while ((s = buf.readLine()) != null) {
                        console.write(s + '\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
    }
}
