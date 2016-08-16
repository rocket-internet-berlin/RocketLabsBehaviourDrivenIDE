package de.rocketlabs.behatide.application.manager.modules;

import de.rocketlabs.behatide.application.configuration.storage.State;
import de.rocketlabs.behatide.application.configuration.storage.StateStorageManager;
import de.rocketlabs.behatide.modules.AbstractModule;
import de.rocketlabs.behatide.modules.behat.BehatModule;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@State(name = "ModuleManager")
public class ModuleManager {

    private List<AbstractModule> loadedModules;

    public List<AbstractModule> getLoadedModules() {
        if (loadedModules == null) {
            loadedModules = new LinkedList<>();
            loadStandardModules();
        }
        return Collections.unmodifiableList(loadedModules);
    }

    private void loadStandardModules() {
        loadModule(new BehatModule());
    }

    public void loadModule(AbstractModule module) {
        loadedModules.add(module);
        StateStorageManager.getInstance().setState(this);
    }
}
