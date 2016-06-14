package de.rocketlabs.behatide.behat;

import com.google.inject.AbstractModule;
import de.rocketlabs.behatide.behat.model.BehatConfiguration;
import de.rocketlabs.behatide.behat.parser.BehatConfigurationReader;
import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;

public class BehatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).to(BehatConfiguration.class);
        bind(ConfigurationReader.class).to(BehatConfigurationReader.class);
    }
}
