package de.rocketlabs.behatide.domain.model;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

import java.io.File;

public abstract class Project {

    private static final String PROJECT_DIRECTORY_NAME = ".behatIde";

    public abstract ProjectMetaData getMetaData();

    protected void initializeProjectDirectory(File parentDirectory) {
        if (parentDirectory.exists() && parentDirectory.isFile()) {
            throw new IllegalArgumentException("Given file is no directory");
        }

        File targetDirectory = new File(parentDirectory.getAbsolutePath() + File.separator + PROJECT_DIRECTORY_NAME);
        if (!targetDirectory.exists()) {
            if (!targetDirectory.mkdirs()) {
                throw new RuntimeException("Could not create project directory");
            }
        }
    }
}
