package de.rocketlabs.behatide.modules.behat.model;

import de.rocketlabs.behatide.domain.model.Suite;

import java.util.*;

public class BehatSuite implements Suite {

    private String name;
    private boolean enabled = true;
    private String type = null;
    private Map<String, Object> settings = new HashMap<>();

    public BehatSuite(String name) {
        this.name = name;
    }

    @Override
    public List<String> getPaths() {
        //noinspection unchecked
        return (List<String>) settings.get("paths");
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getSettingKeys() {
        return settings.keySet();
    }

    public boolean hasSetting(String key) {
        return settings.containsKey(key);
    }

    public Object getSetting(String key) {
        return settings.get(key);
    }

    public void addSetting(String key, Object value) {
        settings.put(key, value);
    }

    @Override
    public List<String> getDefinitionContainerIdentifiers() {
        if (!settings.containsKey("contexts")) {
            return Collections.emptyList();
        }
        //noinspection unchecked
        return (List<String>) settings.get("contexts");
    }
}
