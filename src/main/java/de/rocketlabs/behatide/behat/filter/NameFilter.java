package de.rocketlabs.behatide.behat.filter;

public class NameFilter extends GherkinFilter {

    public NameFilter(String filterCriteria) {
        super(filterCriteria);
    }

    @Override
    public boolean isMatch(Object data) {
        //TODO: Implement
        return false;
    }
}
