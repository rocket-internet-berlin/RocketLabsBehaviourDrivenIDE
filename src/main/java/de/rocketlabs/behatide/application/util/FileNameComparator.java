package de.rocketlabs.behatide.application.util;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        if (f1.isDirectory() && f2.isFile()) {
            return -1;
        }
        if (f1.isFile() && f2.isDirectory()) {
            return 1;
        }
        return f1.getName().compareTo(f2.getName());
    }
}
