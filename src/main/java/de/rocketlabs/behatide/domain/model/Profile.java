package de.rocketlabs.behatide.domain.model;

import java.util.Set;

public interface Profile {

    String getName();

    Suite getSuite(String name);

    Set<String> getSuiteNames();

    void addSuite(Suite suite);
}
