package de.rocketlabs.behatide.application.action;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;

/**
 * @author Jakob Erdmann
 * @since 10.02.17
 */
public abstract class FxCapableAction implements Action {
    protected void executeInFxThread(Runnable action) {
        Platform.runLater(action);
    }

    protected void executeInFxThreadAndWait(Runnable action) {
        PlatformImpl.runAndWait(action);
    }
}
