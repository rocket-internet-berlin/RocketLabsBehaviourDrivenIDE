package de.rocketlabs.behatide.php.model;

import java.util.List;

public class PhpClass {

    private final String name;
    private final List<PhpFunction> members;

    public PhpClass(String name, List<PhpFunction> members) {
        this.name = name;
        this.members = members;
    }
}
