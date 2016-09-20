package de.rocketlabs.behatide.application.util;

import java.io.File;
import java.util.List;

public class FileUtils {

    public static File findCommonParent(List<String> baseFiles) {
        String commonPath = "";

        String[][] folders = new String[baseFiles.size()][];
        for (int i = 0; i < baseFiles.size(); i++) {
            folders[i] = baseFiles.get(i).split(File.separator);
        }
        for (int i = 0; i < folders[0].length; i++) {
            String thisFolder = folders[0][i];
            boolean allMatched = true;
            for (int j = 1; j < folders.length && allMatched; j++) {
                if (folders[j].length < i) {
                    allMatched = false;
                    break;
                }
                allMatched = folders[j][i].equals(thisFolder);
            }
            if (allMatched) {
                commonPath += thisFolder + File.separator;
            } else {
                break;
            }
        }
        return new File(commonPath);
    }
}
