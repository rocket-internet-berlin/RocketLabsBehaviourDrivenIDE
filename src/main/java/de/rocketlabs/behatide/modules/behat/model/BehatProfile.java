package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.model.Suite;
import de.rocketlabs.behatide.modules.behat.filter.GherkinFilter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class BehatProfile implements Profile {

    private String name;
    private String locale;
    private String fallbackLocale;
    private List<String> autoLoadPaths = new LinkedList<>();
    private List<GherkinFilter> filters = new LinkedList<>();
    private Map<String, BehatSuite> suites = new HashMap<>();

    public BehatProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> getPaths() {
        return getAutoLoadPaths();
    }

    @Override
    public void addSuite(@NotNull Suite suite) {
        if (!(suite instanceof BehatSuite)) {
            throw new IllegalArgumentException("Profile must be of type BehatSuite");
        }
        suites.put(suite.getName(), ((BehatSuite) suite));
    }

    @Override
    public BehatSuite getSuite(String name) {
        return suites.get(name);
    }

    @NotNull
    @Override
    public Set<String> getSuiteNames() {
        return suites.keySet();
    }

    @Override
    public List<Suite> getSuites() {
        return Collections.unmodifiableList(new LinkedList<>(suites.values()));
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getFallbackLocale() {
        return fallbackLocale;
    }

    public void setFallbackLocale(String fallbackLocale) {
        this.fallbackLocale = fallbackLocale;
    }

    public void addAutoLoadPath(String autoLoadPath) {
        autoLoadPaths.add(autoLoadPath);
    }

    public List<String> getAutoLoadPaths() {
        return autoLoadPaths;
    }

    public void addFilter(GherkinFilter filter) {
        filters.add(filter);
    }

    public List<GherkinFilter> getFilters() {
        return filters;
    }
}
