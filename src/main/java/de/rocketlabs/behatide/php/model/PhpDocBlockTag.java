package de.rocketlabs.behatide.php.model;

import org.jetbrains.annotations.Nullable;

public class PhpDocBlockTag {

    private final String name;
    private final String content;

    public PhpDocBlockTag(String name, @Nullable String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
