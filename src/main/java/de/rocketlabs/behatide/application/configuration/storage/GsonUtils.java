package de.rocketlabs.behatide.application.configuration.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public abstract class GsonUtils {

    private static Gson gson;
    private static GsonBuilder builder = new GsonBuilder();

    static {
        if (GsonUtils.class.getPackage().getImplementationVersion() == null) {
            builder.setPrettyPrinting();
        }
    }

    @SafeVarargs
    public static <T> void registerType(Class<T> superClass, Class<? extends T>... childClasses) {
        RuntimeTypeAdapterFactory<T> factory = RuntimeTypeAdapterFactory.of(superClass);
        for (Class<? extends T> childClass : childClasses) {
            factory.registerSubtype(childClass);
        }
        builder.registerTypeAdapterFactory(factory);
        gson = null;
    }

    public static <T> void registerInstanceCreator(Class<T> type, InstanceCreator<T> creator) {
        builder.registerTypeAdapter(type, creator);
        gson = null;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = builder.create();
        }
        return gson;
    }
}
