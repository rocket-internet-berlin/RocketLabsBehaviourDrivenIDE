package de.rocketlabs.behatide.application.component;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.ValidationSupport;

import java.util.HashMap;
import java.util.Map;

public class IdeForm extends GridPane {

    private int currentRow = 0;
    private Map<String, Control> controls = new HashMap<>();
    private ValidationSupport validation = new ValidationSupport();

    public IdeForm() {
        setHgap(10);
    }

    public void addControl(String label, Control control) {
        add(new Label(label), 0, currentRow);
        add(control, 1, currentRow);
        currentRow++;

        controls.put(label, control);
    }

    public ValidationSupport getValidation() {
        return validation;
    }

    //TODO add separator
    //TODO add title line
}
