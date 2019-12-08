package me.duvu.tracking.exception;

/**
 * @author beou on 8/9/18 11:27
 */
public class AccessDeninedOrNotExisted extends RuntimeException {

    private static final long serialVersionUID = -5734384696946088140L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AccessDeninedOrNotExisted(String message) {
        super(message);
    }
}
