package de.rocketlabs.behatide.domain.model;


import javafx.scene.image.Image;

public class ProjectType {

    private String typeName;
    private Image icon;
    private ProjectConfiguration defaultConfiguration;

    public ProjectType(String typeName, Image icon, ProjectConfiguration defaultConfiguration) {
        this.typeName = typeName;
        this.icon = icon;
        this.defaultConfiguration = defaultConfiguration;
    }

    public String getTypeName() {
        return typeName;
    }

    public Image getIcon() {
        return icon;
    }

    public ProjectConfiguration getDefaultConfiguration() {
        return defaultConfiguration;
    }
}
