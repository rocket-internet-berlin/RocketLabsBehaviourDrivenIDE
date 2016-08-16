package de.rocketlabs.behatide.application.configuration.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public abstract class GsonUtils {

    private static Gson gson;
    private static GsonBuilder builder = new GsonBuilder();

    static {
        if (GsonUtils.class.getPackage().getImplementationVersion() == null) {
            builder.setPrettyPrinting();
        }
    }

    public static void registerType(RuntimeTypeAdapterFactory<?> adapter) {
        builder.registerTypeAdapterFactory(adapter);
        gson = null;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = builder.create();
        }
        return gson;
    }
}
