package de.rocketlabs.behatide.application.component.startup.wizard;

import org.controlsfx.dialog.Wizard;

public class CreateProjectWizard extends Wizard {

    public CreateProjectWizard() {
        setFlow(
            new LinearFlow(
                new CreateProjectStep1()
            )
        );

    }
}
