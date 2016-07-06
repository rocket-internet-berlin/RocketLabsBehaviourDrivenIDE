package de.rocketlabs.behatide.php.model;

import org.jetbrains.annotations.Nullable;

public class PhpFunction {

    private String name;
    private PhpDocBlock docBlock;

    public PhpFunction(String name, @Nullable PhpDocBlock docBlock) {
        this.name = name;
        this.docBlock = docBlock;
    }
}
