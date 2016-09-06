package de.rocketlabs.behatide.domain.model;

import java.util.List;
import java.util.Set;

public interface Profile {

    String getName();

    Suite getSuite(String name);

    Set<String> getSuiteNames();

    List<Suite> getSuites();

    void addSuite(Suite suite);
}
