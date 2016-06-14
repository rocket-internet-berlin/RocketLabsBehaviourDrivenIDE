package de.rocketlabs.behatide.domain.model;

import java.util.Set;

public interface Configuration {

    Profile getProfile(String name);

    Set<String> getProfileNames();

    void addProfile(Profile profile);
}
