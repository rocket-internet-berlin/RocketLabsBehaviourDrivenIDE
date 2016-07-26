package de.rocketlabs.behatide.application.event;

import java.io.File;

public class FileLoadFailedEvent implements Event {

    private String path;
    private Exception error;

    public FileLoadFailedEvent(String path) {
        this.path = path;
    }

    public FileLoadFailedEvent(String path, Exception error) {
        this.path = path;
        this.error = error;
    }

    public FileLoadFailedEvent(File file) {
        path = file.getAbsolutePath();
    }

    public FileLoadFailedEvent(File file, Exception error) {
        path = file.getAbsolutePath();
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public Exception getError() {
        return error;
    }
}
