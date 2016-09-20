package de.rocketlabs.behatide.php.model;

import de.rocketlabs.behatide.domain.model.DefinitionAnnotation;
import org.jetbrains.annotations.Nullable;

public class PhpDocBlockTag implements DefinitionAnnotation {

    private final String name;
    private final String content;

    public PhpDocBlockTag(String name, @Nullable String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    @Override
    public DefinitionAnnotationType getType() {
        switch (name) {
            case "Then":
                return DefinitionAnnotationType.THEN;
            case "When":
                return DefinitionAnnotationType.WHEN;
            case "Given":
                return DefinitionAnnotationType.GIVEN;
            default:
                return null;
        }
    }

    @Override
    public String getStatement() {
        return content;
    }
}
