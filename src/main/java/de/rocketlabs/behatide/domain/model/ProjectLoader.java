package de.rocketlabs.behatide.domain.model;

public interface ProjectLoader<T extends Project> {

    T loadProject(String configurationFilePath);
}
