package de.rocketlabs.behatide.domain.runner;

import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Project;
import de.rocketlabs.behatide.domain.model.Suite;

import java.nio.file.Path;

public interface TestRunner<T extends Project, K extends Profile, M extends Suite> {

    void runFile(T project, K profile, M suite, Path filePath);
}
