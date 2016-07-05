package de.rocketlabs.behatide.application.configuration.components.storage;

import org.jetbrains.annotations.NonNls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Storage {

    @NonNls
    String value() default "";

    boolean deprecated() default false;

    Class<? extends StateStorage> storageClass() default StateStorage.class;
}

