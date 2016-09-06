package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class BehatConfiguration implements Configuration {

    private Map<String, BehatProfile> profiles = new HashMap<>();

    @Override
    public BehatProfile getProfile(String name) {
        return profiles.get(name);
    }

    @NotNull
    @Override
    public Set<String> getProfileNames() {
        return profiles.keySet();
    }

    @Override
    public List<Profile> getProfiles() {
        return Collections.unmodifiableList(new LinkedList<>(profiles.values()));
    }

    @Override
    public void addProfile(@NotNull Profile profile) {
        if (!(profile instanceof BehatProfile)) {
            throw new IllegalArgumentException("Profile must be of type BehatProfile");
        }
        profiles.put(profile.getName(), ((BehatProfile) profile));
    }
}
