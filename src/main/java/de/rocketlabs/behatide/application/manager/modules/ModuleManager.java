package de.rocketlabs.behatide.application.manager.modules;

import de.rocketlabs.behatide.modules.AbstractModule;
import de.rocketlabs.behatide.modules.behat.BehatModule;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ModuleManager {

    private static ModuleManager instance = new ModuleManager();
    private List<AbstractModule> loadedModules = new LinkedList<>();

    private ModuleManager() {
        registerModule(new BehatModule());
    }

    private void registerModule(AbstractModule module) {
        loadedModules.add(module);
        module.configureGson();
    }

    public static ModuleManager getInstance() {
        return instance;
    }

    public AbstractModule forName(String name) {
        for (AbstractModule loadedModule : loadedModules) {
            if (Objects.equals(loadedModule.getClass().getSimpleName(), name)) {
                return loadedModule;
            }
        }
        throw new IllegalArgumentException("Unknown module " + name);
    }

    public List<AbstractModule> getLoadedModules() {
        return Collections.unmodifiableList(loadedModules);
    }
}
