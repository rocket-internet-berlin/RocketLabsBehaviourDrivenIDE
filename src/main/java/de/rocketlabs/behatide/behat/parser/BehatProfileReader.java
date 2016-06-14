package de.rocketlabs.behatide.behat.parser;

import com.google.inject.Inject;
import de.rocketlabs.behatide.behat.model.BehatProfile;
import de.rocketlabs.behatide.behat.model.FilterType;

import java.util.Map;


public final class BehatProfileReader {

    private static final String DEFAULT_LOCALE = "en";
    private static final String DEFAULT_FALLBACK_LOCALE = "en";
    private static final String DEFAULT_AUTOLOAD_PATH = "%paths.base%/features/bootstrap";

    private BehatSuiteReader suiteReader;

    @Inject
    public BehatProfileReader(BehatSuiteReader suiteReader) {
        this.suiteReader = suiteReader;
    }

    public BehatProfile read(String profileName, Map profileData) {
        BehatProfile profile;
        if (profileData.get("<<") instanceof Map) {
            profile = read(profileName, ((Map) profileData.get("<<")));
        } else {
            profile = new BehatProfile(profileName);
        }

        readAutoLoaderExtensionConfiguration(profile, profileData);
        readSuiteExtensionConfiguration(profile, profileData);
        readGherkinExtensionConfiguration(profile, profileData);
        readTranslationExtensionConfiguration(profile, profileData);
        //ignore output extension configuration
        //ignore exception extension configuration
        //ignore call extension configuration
        //ignore tester extension configuration

        return profile;
    }

    private void readTranslationExtensionConfiguration(BehatProfile profile, Map profileData) {
        String locale = DEFAULT_LOCALE;
        String fallbackLocale = DEFAULT_FALLBACK_LOCALE;

        if (profileData.get("translation") instanceof Map) {
            Map translationData = ((Map) profileData.get("translation"));
            if (translationData.get("locale") instanceof String) {
                locale = ((String) translationData.get("locale"));
            }

            if (translationData.get("fallback_locale") instanceof String) {
                fallbackLocale = ((String) translationData.get("fallback_locale"));
            }
        }

        profile.setLocale(locale);
        profile.setFallbackLocale(fallbackLocale);
    }

    private void readAutoLoaderExtensionConfiguration(BehatProfile profile, Map profileData) {
        if (profileData.get("autoload") instanceof Map) {
            Map<?, ?> autoLoadData = (Map) profileData.get("autoload");
            autoLoadData
                    .values()
                    .stream()
                    .filter(value -> value instanceof String)
                    .forEach(value -> profile.addAutoLoadPath((String) value));
        } else if (profileData.get("autoload") instanceof String) {
            profile.addAutoLoadPath((String) profileData.get("autoload"));
        } else {
            profile.addAutoLoadPath(DEFAULT_AUTOLOAD_PATH);
        }
    }

    private void readGherkinExtensionConfiguration(BehatProfile profile, Map profileData) {
        if (profileData.get("gherkin") instanceof Map) {
            Map gherkinData = (Map) profileData.get("gherkin");
            //ignore cache node
            if (gherkinData.get("filters") instanceof Map) {
                Map<?, ?> filters = (Map) gherkinData.get("filters");
                filters.keySet()
                        .stream()
                        .filter(key -> key instanceof String)
                        .forEach(key -> {
                            FilterType filterType = FilterType.valueOf(((String) key).toUpperCase());
                            profile.addFilter(filterType.createFilter((String) filters.get(key)));
                        });
            }
        }
    }

    private void readSuiteExtensionConfiguration(BehatProfile profile, Map profileData) {
        if (profileData.get("suites") instanceof Map) {
            Map<?, ?> suites = (Map) profileData.get("suites");
            suites.keySet()
                    .stream()
                    .filter(key -> key instanceof String)
                    .forEach(key -> profile.addSuite(suiteReader.readSuite((String) key, suites.get(key))));
        }
        /**
         $builder
         ->defaultValue(array('default' => array(
         'enabled'    => true,
         'type'       => null,
         'settings'   => array()
         )))
         ->treatNullLike(array())
         ->treatFalseLike(array())
         ->useAttributeAsKey('name')
         ->prototype('array')
         ->beforeNormalization()
         ->ifTrue(function ($suite) {
         return is_array($suite) && count($suite);
         })
         ->then(function ($suite) {
         $suite['settings'] = isset($suite['settings'])
         ? $suite['settings']
         : array();

         foreach ($suite as $key => $val) {
         $suiteKeys = array('enabled', 'type', 'settings');
         if (!in_array($key, $suiteKeys)) {
         $suite['settings'][$key] = $val;
         unset($suite[$key]);
         }
         }

         return $suite;
         })
         ->end()
         ->normalizeKeys(false)
         ->addDefaultsIfNotSet()
         ->treatTrueLike(array('enabled' => true))
         ->treatNullLike(array('enabled' => true))
         ->treatFalseLike(array('enabled' => false))
         ->children()
         ->booleanNode('enabled')
         ->info('Enables/disables suite')
         ->defaultTrue()
         ->end()
         ->scalarNode('type')
         ->info('Specifies suite type')
         ->defaultValue(null)
         ->end()
         ->arrayNode('settings')
         ->info('Specifies suite extra settings')
         ->defaultValue(array())
         ->useAttributeAsKey('name')
         ->prototype('variable')->end()
         ->end()
         ->end()
         ->end()
         */
    }
}
