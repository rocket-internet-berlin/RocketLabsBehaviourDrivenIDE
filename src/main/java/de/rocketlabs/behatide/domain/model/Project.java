package de.rocketlabs.behatide.domain.model;

import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;

import java.util.List;
import java.util.Map;

public interface Project {

    /**
     * @return A regular expression matching files that should be used for the specific projectContext type
     */
    String getFileMask();

    String getTitle();

    Map<String, String> getPathReplacements();

    ProjectMetaData getMetaData();

    Configuration getConfiguration();

    List<DefinitionContainer> getDefinitions(List<String> definitionContainerIdentifiers);

    Map<String, byte[]> getFileHashes();
}
