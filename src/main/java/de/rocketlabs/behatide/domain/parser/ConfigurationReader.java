package de.rocketlabs.behatide.domain.parser;

import de.rocketlabs.behatide.domain.model.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ConfigurationReader<T extends Configuration> {

    T read(InputStream fileStream) throws IOException;

    T read(File file) throws IOException;
}
