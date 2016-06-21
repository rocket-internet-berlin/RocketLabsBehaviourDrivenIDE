package de.rocketlabs.behatide.behat.model;

import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public void addProfile(@NotNull Profile profile) {
        if (!(profile instanceof BehatProfile)) {
            throw new IllegalArgumentException("Profile must be of type BehatProfile");
        }
        profiles.put(profile.getName(), ((BehatProfile) profile));
    }
}
