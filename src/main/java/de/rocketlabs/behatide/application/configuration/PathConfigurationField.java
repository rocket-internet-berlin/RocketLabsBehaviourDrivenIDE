package de.rocketlabs.behatide.application.configuration;

import de.rocketlabs.behatide.domain.ConfigurationField;
import de.rocketlabs.behatide.domain.model.FieldType;
import javafx.beans.property.SimpleStringProperty;

public class PathConfigurationField extends SimpleStringProperty implements ConfigurationField<String> {

    private final String name;
    private String value;

    public PathConfigurationField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.PATH_FIELD;
    }

    @Override
    public String getData() {
        return value;
    }

    @Override
    public void setData(String value) {
        this.value = value;
    }
}
