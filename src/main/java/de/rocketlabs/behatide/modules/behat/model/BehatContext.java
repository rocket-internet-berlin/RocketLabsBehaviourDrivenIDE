package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.php.model.PhpDocBlock;
import de.rocketlabs.behatide.php.model.PhpDocBlockTag;
import de.rocketlabs.behatide.php.model.PhpFunction;

import java.util.*;

@State(name = "BehatContextTagsGivenWhenThen")
public class BehatContext extends HashMap<String, List<PhpFunction>>
{
    public boolean hasContext(String classname) {
        return this.containsKey(classname);
    }

    public List<PhpFunction> getContextFunctionByClassName(String className) {
        return this.get(className);
    }

    public Map<String, String> getGivenWhenThenTagsByClassName(String className) {
        Map<String, String> tagsList = new HashMap<>();

        if (hasContext(className)) {
            List<PhpFunction> tags = getContextFunctionByClassName(className);
            for (PhpFunction func : tags) {
                PhpDocBlock dockBlock = func.getDocBlock();
                if (dockBlock != null && dockBlock.hasTags()) {
                    for (PhpDocBlockTag phpDocBlockTag : dockBlock.getTags()) {
                        if (phpDocBlockTag.getName().matches("(Then|Given|When)")) {
                            tagsList.put(phpDocBlockTag.getName(),phpDocBlockTag.getContent());
                        }
                    }
                }
            }
        }

        return tagsList;
    }
}
