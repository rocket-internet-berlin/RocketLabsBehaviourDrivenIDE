package de.rocketlabs.behatide.behat.model;

import de.rocketlabs.behatide.domain.model.Suite;

public class BehatSuite implements Suite {

    private String name;

    public BehatSuite(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
