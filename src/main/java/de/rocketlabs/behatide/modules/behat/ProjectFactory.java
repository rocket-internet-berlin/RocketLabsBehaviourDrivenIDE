package de.rocketlabs.behatide.modules.behat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.behat.model.BehatProfile;
import de.rocketlabs.behatide.modules.behat.model.BehatSuite;
import de.rocketlabs.behatide.modules.behat.model.Project;
import de.rocketlabs.behatide.modules.behat.model.ProjectConfiguration;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;
import de.rocketlabs.behatide.php.ParseException;
import de.rocketlabs.behatide.php.PhpParser;
import de.rocketlabs.behatide.php.model.PhpClass;
import de.rocketlabs.behatide.php.model.PhpFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProjectFactory {

    private static final String[] AUTO_LOADER_PATHS = new String[]{
        "../../../autoload.php",
        "vendor/autoload.php",
        "../vendor/autoload.php",
    };

    private static Injector injector = Guice.createInjector(new BehatModule());
    private static BehatConfigurationReader configurationReader =
        (BehatConfigurationReader) injector.getInstance(ConfigurationReader.class);

    public static de.rocketlabs.behatide.domain.model.Project generateProject(ProjectConfiguration configuration) {
        Project project = new de.rocketlabs.behatide.modules.behat.model
            .Project();
        project.setProjectLocation(configuration.getProjectLocation());
        project.setBehatConfigurationFile(configuration.getBehatConfigurationFile());
        project.setBehatExecutablePath(configuration.getBehatExecutable());
        project.setTitle(configuration.getTitle());
        loadConfigurationFile(project);
        loadBehatDefinitions(project, configuration);

        Map<StorageParameter, String> storageParameters = new HashMap<StorageParameter, String>() {{
            put(StorageParameter.STORAGE_DIRECTORY, project.getProjectLocation());
        }};

        StateStorageManager.getInstance().setState(project, storageParameters);
        StateStorageManager.getInstance().save();

        return project;
    }

    private static void loadConfigurationFile(Project project) {
        String filePath = project.getBehatConfigurationFile();
        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            project.setConfiguration(configurationReader.read(in));
        } catch (Exception e) {
            throw new RuntimeException("Could not load behat configuration", e);
        }
        setFileHash(project, filePath);
    }

    private static void setFileHash(Project project, String filePath) {
        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            project.setFileHash(filePath, DigestUtils.md5(in));
        } catch (Exception e) {
            throw new RuntimeException("Could not load behat configuration", e);
        }
    }

    private static void loadBehatDefinitions(Project project, ProjectConfiguration configuration) {
        Set<String> classesSet = buildClassSet(project);
        Map<String, PhpFile> parsedFiles = loadPhpClasses(project, classesSet, configuration);
        Map<String, PhpClass> behatDefinitions = getBehatDefinitions(parsedFiles);
        project.setAvailableDefinitions(behatDefinitions);
    }

    private static Set<String> buildClassSet(Project project) {
        Set<String> classesSet = new HashSet<>();
        project.getConfiguration().getProfileNames().forEach(profileName -> {
            BehatProfile profile = project.getConfiguration().getProfile(profileName);
            profile.getSuiteNames().forEach(suiteName -> {
                BehatSuite suite = profile.getSuite(suiteName);
                classesSet.addAll(suite.getDefinitionContainerIdentifiers());
            });
        });
        return classesSet;
    }

    private static Map<String, PhpFile> loadPhpClasses(Project project, Set<String> classesSet, ProjectConfiguration configuration) {
        ProcessBuilder builder = configureProcessBuilder(classesSet, configuration);
        Map<String, PhpFile> parsedFiles = new HashMap<>();
        try {
            Process process = builder.start();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                for (String className : classesSet) {
                    String path = in.readLine();
                    if (path == null) {
                        break;
                    }
                    parsedFiles.put(className, PhpParser.parse(new FileInputStream(path)));
                    setFileHash(project, path);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return parsedFiles;
    }

    @NotNull
    private static ProcessBuilder configureProcessBuilder(final Set<String> classesSet,
                                                          final ProjectConfiguration configuration) {
        List<String> cmdList = new LinkedList<String>() {{
            add("php");
            add(Project.class.getResource("/php/loadClass.php").getFile());
            add(getAutoloadPhpPath(configuration));
            addAll(classesSet);
        }};
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmdList);
        return builder;
    }

    @NotNull
    private static String getAutoloadPhpPath(ProjectConfiguration configuration) {
        File f = new File(configuration.getBehatExecutable());
        String parentPath = f.getParent();

        for (String autoLoaderPath : AUTO_LOADER_PATHS) {
            Path path = Paths.get(parentPath, autoLoaderPath);
            if (Files.exists(path)) {
                return path.toAbsolutePath().toString();
            }
        }

        throw new RuntimeException("autoload not found");
    }

    private static Map<String, PhpClass> getBehatDefinitions(Map<String, PhpFile> loadedPhpFiles) {
        Function<Map.Entry<String, PhpFile>, PhpClass> findClass = entry -> {
            PhpFile phpFile = entry.getValue();
            for (PhpClass phpClass : phpFile.getClasses()) {
                if (entry.getKey().equals('\\' + phpFile.getNamespace() + '\\' + phpClass.getName())) {
                    return phpClass;
                }
            }
            return null;
        };

        return loadedPhpFiles.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, findClass));
    }
}
