package de.rocketlabs.behatide.behat.filter;

public class TagFilter extends GherkinFilter {

    public TagFilter(String filterCriteria) {
        super(filterCriteria);
    }

    @Override
    public boolean isMatch(Object data) {
        //TODO: Implement
        return false;
    }
}
