package de.rocketlabs.behatide.application.configuration.storage;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.util.HashSet;
import java.util.Set;

public interface ExtendableSerializable<T extends ExtendableSerializable> {

    Set<Class<?>> registeredClasses = new HashSet<>();

    default void registerClass() {
        if (!registeredClasses.contains(this.getClass())) {
            registeredClasses.add(this.getClass());
            //noinspection unchecked
            getStaticAdapter().registerSubtype((Class<? extends T>) this.getClass());
        }
    }

    RuntimeTypeAdapterFactory<T> getStaticAdapter();
}
