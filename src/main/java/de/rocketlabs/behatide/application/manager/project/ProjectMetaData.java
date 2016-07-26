package de.rocketlabs.behatide.application.manager.project;

public class ProjectMetaData implements Cloneable {

    private String path;
    private String title;

    public ProjectMetaData(String path, String title) {
        this.path = path;
        this.title = title;
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

    @Override
    public int hashCode() {
        return path.hashCode() + title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProjectMetaData
            && title.equals(((ProjectMetaData) obj).title)
            && path.equals(((ProjectMetaData) obj).path);
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
