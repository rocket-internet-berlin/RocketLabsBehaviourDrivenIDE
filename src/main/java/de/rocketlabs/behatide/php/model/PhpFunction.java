package de.rocketlabs.behatide.php.model;

import de.rocketlabs.behatide.domain.model.Definition;
import de.rocketlabs.behatide.domain.model.DefinitionAnnotation;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PhpFunction implements Definition {

    private String name;
    private PhpDocBlock docBlock;

    public PhpFunction(String name, @Nullable PhpDocBlock docBlock) {
        this.name = name;
        this.docBlock = docBlock;
    }

    public PhpDocBlock getDocBlock() {
        return docBlock;
    }

    @Override
    @Nullable
    public String getMethodName() {
        return name;
    }

    @Override
    public String getDescription() {
        if (docBlock == null) {
            return null;
        }
        return docBlock.getDescription();
    }

    @Override
    public List<DefinitionAnnotation> getAnnotations() {
        if (docBlock == null || !docBlock.hasTags()) {
            return new LinkedList<>();
        }
        return docBlock.getTags()
            .stream()
            .filter(tag -> Objects.equals(tag.getName(), "Then")
                || Objects.equals(tag.getName(), "When")
                || Objects.equals(tag.getName(), "Given"))
            .collect(Collectors.toList());
    }
}
