package de.rocketlabs.behatide.domain.model;

import java.util.List;

public interface Suite {

    List<String> getBaseFiles();

    String getName();

    String getProjectFileMask();
}
