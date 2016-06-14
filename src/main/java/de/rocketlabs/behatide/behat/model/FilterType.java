package de.rocketlabs.behatide.behat.model;

import de.rocketlabs.behatide.behat.filter.*;

import java.lang.reflect.InvocationTargetException;

public enum FilterType {
    TAGS(TagFilter.class),
    ROLE(RoleFilter.class),
    NAME(NameFilter.class),
    NARRATIVE(NarrativeFilter.class);

    private Class<? extends GherkinFilter> filterClass;

    FilterType(Class<? extends GherkinFilter> filterClass) {
        this.filterClass = filterClass;
    }

    public GherkinFilter createFilter(String filterCriteria) {
        try {
            return filterClass.getConstructor(String.class).newInstance(filterCriteria);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Provided class " + filterClass.getName() + " seems not to extend GherkinFilter");
        }
    }
}
