package de.rocketlabs.behatide.modules.behat.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.rocketlabs.behatide.application.manager.project.ProjectMetaData;
import de.rocketlabs.behatide.domain.parser.ConfigurationReader;
import de.rocketlabs.behatide.modules.behat.BehatModule;
import de.rocketlabs.behatide.modules.behat.parser.BehatConfigurationReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Project extends de.rocketlabs.behatide.domain.model.Project {

    Injector injector = Guice.createInjector(new BehatModule());
    BehatConfigurationReader configurationReader = (BehatConfigurationReader) injector.getInstance(
        ConfigurationReader.class);

    private String projectLocation;
    private String behatExecutablePath;
    private String behatConfigurationFile;
    private BehatConfiguration configuration;
    private byte[] configurationFileHash;

    @Override
    public ProjectMetaData getMetaData() {
        return null;
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
        project.initializeProjectDirectory(new File(configuration.getProjectLocation()));
        project.projectLocation = configuration.getProjectLocation();
        project.behatConfigurationFile = configuration.getBehatExecutable();
        project.loadConfigurationFile();

        return project;
    }
}
