package me.duvu.tracking.exception;

/**
 * @author beou on 9/25/18 17:09
 */
public class InvalidFormatException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
