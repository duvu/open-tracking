package me.duvu.tracking.exception;

/**
 * @author beou on 1/15/18 10:21
 */
public class DeviceAlreadyExistedException extends RuntimeException {

    private static final long serialVersionUID = 264459736457420154L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DeviceAlreadyExistedException(String message) {
        super("[DeviceAlreadyExistedException] " + message);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DeviceAlreadyExistedException() {
        super("[DeviceAlreadyExistedException]");
    }
}
