package de.rocketlabs.behatide.application.component.startup.wizard;

import de.rocketlabs.behatide.application.wizard.LinearValidationFlow;
import org.controlsfx.dialog.Wizard;

public class CreateProjectWizard extends Wizard {

    private CreateProjectStep1 createProjectStep1;

    public CreateProjectWizard() {
        createProjectStep1 = new CreateProjectStep1();
        LinearValidationFlow flow = new LinearValidationFlow(createProjectStep1);
        invalidProperty().bind(flow.invalidProperty());
        setFlow(flow);
    }

    @Override
    public Object getUserData() {
        return createProjectStep1.getUserData();
    }
}
