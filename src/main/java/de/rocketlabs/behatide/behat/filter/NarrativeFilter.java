package de.rocketlabs.behatide.behat.filter;

public class NarrativeFilter extends GherkinFilter {

    public NarrativeFilter(String filterCriteria) {
        super(filterCriteria);
    }

    @Override
    public boolean isMatch(Object data) {
        //TODO: Implement
        return false;
    }
}
