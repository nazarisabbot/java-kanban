package tracker.controllers;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }
}
