package de.rocketlabs.behatide.application.configuration.components.storage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Storage {

    Class<? extends StateStorage> storageClass() default StateStorage.class;
}

