package de.rocketlabs.behatide.modules.behat.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.domain.model.Configuration;
import de.rocketlabs.behatide.domain.model.Profile;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.behat.BehatModule;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;
import de.rocketlabs.behatide.php.ParseException;
import de.rocketlabs.behatide.php.PhpParser;
import de.rocketlabs.behatide.php.model.PhpFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@State(name = "Project")
public class Project implements de.rocketlabs.behatide.domain.model.Project {

    public static final String AUTOLOAD_PHP_3 = "/../../../autoload.php";
    public static final String VENDOR_AUTOLOAD_PHP2 = "/vendor/autoload.php";
    public static final String VENDOR_AUTOLOAD_PHP_1 = "/../vendor/autoload.php";
    private transient Injector injector = Guice.createInjector(new BehatModule());
    private transient BehatConfigurationReader configurationReader =
        (BehatConfigurationReader) injector.getInstance(ConfigurationReader.class);

    private String title;
    private String projectLocation;
    private String behatExecutablePath;
    private String behatConfigurationFile;
    private BehatConfiguration configuration;
    private byte[] configurationFileHash;

    private Map parsedPhpFiles;

    @Override
    public String getFileMask() {
        return ".*\\.feature$";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Map<String, String> getPathReplacements() {
        return new HashMap<String, String>() {{
            put("%paths.base%", new File(behatConfigurationFile).getParentFile().getAbsolutePath());
        }};
    }

    @Override
    public ProjectMetaData getMetaData() {
        return new ProjectMetaData(projectLocation, title, "BehatModule");
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private void loadConfigurationFile() {
        try (InputStream in = Files.newInputStream(Paths.get(behatConfigurationFile))) {
            configuration = configurationReader.read(in);
        } catch (Exception e) {
            throw new RuntimeException("Could not load behat configuration", e);
        }
        configurationFileHash = getFileHash(behatConfigurationFile);
    }

    private byte[] getFileHash(String filePath) {
        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            return DigestUtils.md5(in);
        } catch (Exception e) {
            throw new RuntimeException("Could not load behat configuration", e);
        }
    }

    static Project generateProject(ProjectConfiguration configuration) {
        Project project = new Project();
        project.projectLocation = configuration.getProjectLocation();
        project.behatConfigurationFile = configuration.getBehatConfigurationFile();
        project.behatExecutablePath = configuration.getBehatExecutable();
        project.title = configuration.getTitle();
        project.loadConfigurationFile();

        Map<StorageParameter, String> storageParameters = new HashMap<StorageParameter, String>() {{
            put(StorageParameter.STORAGE_DIRECTORY, project.projectLocation);
        }};

        Map<String, PhpFile> loadedPhpFiles = new HashMap<>();
        long l = System.currentTimeMillis();

        Set<String> classesSet = new HashSet<>();
        project.configuration.getProfileNames().forEach(profileName -> {
            BehatProfile profile = project.configuration.getProfile(profileName);
            profile.getSuiteNames().forEach(suiteName -> {
                BehatSuite suite = profile.getSuite(suiteName);
                classesSet.addAll(suite.getSettingContexts());
            });
        });

        List<String> cmdList = new LinkedList<String>() {{
            add("php");
            add(Project.class.getResource("/php/loadClass.php").getFile());
            add(getAutoloadPhpPath(configuration));
            addAll(classesSet);
        }};


        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmdList);
        try {
            Process process = builder.start();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                for (String className : classesSet) {
                    String path = in.readLine();
                    if (path == null) {
                        break;
                    }
                    PhpFile file = PhpParser.parse(new FileInputStream(path));
                    loadedPhpFiles.put(className, file);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        StateStorageManager.getInstance().setState(project, storageParameters);
        StateStorageManager.getInstance().save();

        return project;
    }

    public Map getParsedPhpFiles()
    {
        return parsedPhpFiles;
    }

    @NotNull
    private static String getAutoloadPhpPath(ProjectConfiguration configuration) {

        File f = new File(configuration.getBehatExecutable());
        String parentPath = f.getParent();

        if (fileExists(parentPath, VENDOR_AUTOLOAD_PHP_1)) {
            return parentPath + VENDOR_AUTOLOAD_PHP_1;
        }
        if (fileExists(parentPath, VENDOR_AUTOLOAD_PHP2)) {
            return parentPath + VENDOR_AUTOLOAD_PHP2;
        }
        if (fileExists(parentPath, AUTOLOAD_PHP_3)) {
            return parentPath + AUTOLOAD_PHP_3;
        }

        throw new RuntimeException("autoload not found");
    }

    private static boolean fileExists(String filePath, String file)
    {
        File f = new File(filePath + file);

        return f.exists();
    }

}
