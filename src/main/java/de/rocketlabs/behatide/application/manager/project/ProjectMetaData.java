package de.rocketlabs.behatide.application.manager.project;

public class ProjectMetaData implements Cloneable {

    private String path;
    private String title;
    private String moduleName;

    public ProjectMetaData(String path, String title, String moduleName) {
        this.path = path;
        this.title = title;
        this.moduleName = moduleName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public int hashCode() {
        return path.hashCode() + title.hashCode() + moduleName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProjectMetaData
            && title.equals(((ProjectMetaData) obj).title)
            && path.equals(((ProjectMetaData) obj).path)
            && moduleName.equals(((ProjectMetaData) obj).moduleName);
    }

    @Override
    public ProjectMetaData clone() {
        try {
            return (ProjectMetaData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
