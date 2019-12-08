package me.duvu.tracking.exception;

/**
 * @author beou on 3/12/18 01:51
 */
public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4050915023983883900L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ObjectNotFoundException(String message) {
        super("[ObjectNotFoundException] " + message);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ObjectNotFoundException() {
        super("[ObjectNotFoundException]");
    }
}
