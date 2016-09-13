package de.rocketlabs.behatide.php.model;

import java.util.List;

public class PhpFile {

    private final String namespace;
    private final List<PhpClass> classes;

    public PhpFile(String namespace, List<PhpClass> classes) {
        this.namespace = namespace;
        this.classes = classes;
    }

    public String getNamespace() {
        return namespace;
    }

    public List<PhpClass> getClasses() {
        return classes;
    }
}
