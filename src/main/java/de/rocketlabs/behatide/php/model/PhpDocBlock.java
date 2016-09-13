package de.rocketlabs.behatide.php.model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhpDocBlock {

    private final String description;
    private final List<PhpDocBlockTag> tags;

    public PhpDocBlock(@Nullable String description, @Nullable List<PhpDocBlockTag> tags) {
        this.description = description;
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public List<PhpDocBlockTag> getTags() {
        return tags;
    }

    public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }
}
