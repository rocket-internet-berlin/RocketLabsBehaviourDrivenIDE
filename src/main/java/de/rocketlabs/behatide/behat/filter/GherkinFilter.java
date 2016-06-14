package de.rocketlabs.behatide.behat.filter;

import de.rocketlabs.behatide.domain.parser.Filter;

//TODO: Use correct data type
public abstract class GherkinFilter implements Filter<Object> {

    protected String filterCriteria;

    public GherkinFilter(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }
}
