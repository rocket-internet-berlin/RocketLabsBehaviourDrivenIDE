package de.rocketlabs.behatide.application.wizard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;

import java.util.Collection;
import java.util.Optional;

public class LinearValidationFlow extends Wizard.LinearFlow {

    private BooleanProperty invalidProperty = new SimpleBooleanProperty();

    public LinearValidationFlow(Collection<WizardPane> pages) {
        super(pages);
    }

    public LinearValidationFlow(WizardPane... pages) {
        super(pages);
    }

    @Override
    public Optional<WizardPane> advance(WizardPane currentPage) {
        invalidProperty.unbind();
        Optional<WizardPane> advance = super.advance(currentPage);
        advance.ifPresent(pane -> {
            if (pane instanceof ValidationWizardStep) {
                invalidProperty.bind(((ValidationWizardStep) pane).invalidProperty());
            }
        });
        return advance;
    }

    public BooleanProperty invalidProperty() {
        return invalidProperty;
    }
}
