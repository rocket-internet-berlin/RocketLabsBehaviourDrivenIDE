package de.rocketlabs.behatide.modules.behat.parser.settings;

public interface BehatSettingsReader {

    boolean supports(String key);

    Object readSetting(String key, Object data);
}
