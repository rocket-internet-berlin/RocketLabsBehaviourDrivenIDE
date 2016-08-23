package de.rocketlabs.behatide.modules.behat.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.configuration.storage.StorageParameter;
import de.rocketlabs.behatide.application.configuration.storage.state.State;
import de.rocketlabs.behatide.application.configuration.storage.state.StateStorageManager;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.behat.BehatModule;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@State(name = "Project")
public class Project extends de.rocketlabs.behatide.domain.model.Project {

    private transient Injector injector = Guice.createInjector(new BehatModule());
    private transient BehatConfigurationReader configurationReader =
        (BehatConfigurationReader) injector.getInstance(ConfigurationReader.class);

    private String title;
    private String projectLocation;
    private String behatExecutablePath;
    private String behatConfigurationFile;
    private BehatConfiguration configuration;
    private byte[] configurationFileHash;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ProjectMetaData getMetaData() {
        return new ProjectMetaData(projectLocation, title, "BehatModule");
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

    public static Project generateProject(ProjectConfiguration configuration) {
        Project project = new Project();
        project.projectLocation = configuration.getProjectLocation();
        project.behatConfigurationFile = configuration.getBehatConfigurationFile();
        project.behatExecutablePath = configuration.getBehatExecutable();
        project.title = configuration.getTitle();
        project.loadConfigurationFile();

        Map<StorageParameter, String> storageParameters = new HashMap<StorageParameter, String>() {{
            put(StorageParameter.STORAGE_DIRECTORY, project.projectLocation);
        }};

        StateStorageManager.getInstance().setState(project, storageParameters);
        StateStorageManager.getInstance().save();

        return project;
    }

}
