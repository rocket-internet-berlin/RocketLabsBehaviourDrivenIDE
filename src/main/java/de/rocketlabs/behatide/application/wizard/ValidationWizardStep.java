package de.rocketlabs.behatide.application.wizard;

import javafx.beans.property.ReadOnlyBooleanProperty;
import org.jetbrains.annotations.NotNull;

public interface ValidationWizardStep {

    @NotNull ReadOnlyBooleanProperty invalidProperty();
}
