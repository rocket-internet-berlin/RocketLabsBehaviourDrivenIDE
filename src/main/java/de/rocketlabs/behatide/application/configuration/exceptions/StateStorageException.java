package de.rocketlabs.behatide.application.configuration.exceptions;

public class StateStorageException extends RuntimeException {

    public StateStorageException() {
        super();
    }

    public StateStorageException(String message) {
        super(message);
    }

    public StateStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateStorageException(Throwable cause) {
        super(cause);
    }

    protected StateStorageException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
