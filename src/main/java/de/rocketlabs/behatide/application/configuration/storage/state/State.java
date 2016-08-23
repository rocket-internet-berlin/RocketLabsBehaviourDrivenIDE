package de.rocketlabs.behatide.application.configuration.storage.state;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface State {

    String name();

    Storage[] storages() default {@Storage};
}
