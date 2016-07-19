package de.rocketlabs.behatide.application.projects;

public class RecentProjectModel implements Cloneable {

    private String path;
    private String title;

    public RecentProjectModel(String path, String title) {
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
        return obj instanceof RecentProjectModel
                && title.equals(((RecentProjectModel) obj).title)
                && path.equals(((RecentProjectModel) obj).title);
    }

    @Override
    public RecentProjectModel clone() {
        try {
            return (RecentProjectModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
