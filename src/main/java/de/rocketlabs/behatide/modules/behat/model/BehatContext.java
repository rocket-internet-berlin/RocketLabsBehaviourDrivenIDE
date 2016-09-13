package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.php.model.PhpFunction;

import java.util.HashMap;
import java.util.List;

@State(name = "BehatContextTagsGivenWhenThen")
public class BehatContext extends HashMap<String, List<PhpFunction>>
{
    public boolean hasContext(String classname) {
        return containsKey(classname);
    }

    public List<PhpFunction> getContextFunctionByClassName(String className) {
        return get(className);
    }

}
