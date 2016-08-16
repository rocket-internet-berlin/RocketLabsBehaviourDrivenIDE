package de.rocketlabs.behatide.modules;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import de.rocketlabs.behatide.application.configuration.storage.ExtendableSerializable;
import de.rocketlabs.behatide.application.configuration.storage.GsonUtils;
import de.rocketlabs.behatide.domain.model.ProjectType;

import java.util.List;

public abstract class AbstractModule extends com.google.inject.AbstractModule implements
                                                                              ExtendableSerializable<AbstractModule> {

    private static final RuntimeTypeAdapterFactory<AbstractModule> adapter =
        RuntimeTypeAdapterFactory.of(AbstractModule.class);

    @Override
    public RuntimeTypeAdapterFactory<AbstractModule> getStaticAdapter() {
        return adapter;
    }

    public AbstractModule() {
        registerClass();
        GsonUtils.registerType(getStaticAdapter());
    }

    public abstract List<ProjectType> getProjectTypes();

}
