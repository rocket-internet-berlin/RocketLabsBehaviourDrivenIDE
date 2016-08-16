package de.rocketlabs.behatide.application.component;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class IdeForm extends GridPane {

    private int currentRow = 0;
    private Map<String, Control> controls = new HashMap<>();

    public void addControl(String label, Control control) {
        add(new Label(label), 0, currentRow);
        add(control, 1, currentRow);
        currentRow++;

        controls.put(label, control);
    }

    //TODO add separator
    //TODO add title line

}
