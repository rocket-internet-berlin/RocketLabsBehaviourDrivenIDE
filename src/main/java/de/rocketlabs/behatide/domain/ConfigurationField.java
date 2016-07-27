package de.rocketlabs.behatide.domain;

import de.rocketlabs.behatide.domain.model.FieldType;

public interface ConfigurationField<T> {

    String getName();

    FieldType getFieldType();

    T getData();

    void setData(T value);
}
