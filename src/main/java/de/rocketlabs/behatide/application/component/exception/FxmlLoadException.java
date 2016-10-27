package de.rocketlabs.behatide.application.component.exception;

import org.jetbrains.annotations.NonNls;

public class FxmlLoadException extends RuntimeException {

    public FxmlLoadException() {
        super();
    }

    public FxmlLoadException(@NonNls String message) {
        super(message);
    }

    public FxmlLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FxmlLoadException(Throwable cause) {
        super(cause);
    }

    protected FxmlLoadException(String message,
                                Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
