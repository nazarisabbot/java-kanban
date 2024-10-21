package tracker.exceptions;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(final String message, Throwable e) {
        super(message, e);
    }
}
