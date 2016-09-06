package de.rocketlabs.behatide.domain.model;

import java.util.List;
import java.util.Set;

public interface Configuration {

    Profile getProfile(String name);

    Set<String> getProfileNames();

    List<Profile> getProfiles();

    void addProfile(Profile profile);
}
