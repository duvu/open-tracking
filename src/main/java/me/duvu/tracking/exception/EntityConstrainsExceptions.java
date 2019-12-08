package me.duvu.tracking.exception;

/**
 * @author beou on 3/11/18 16:24
 */
public class EntityConstrainsExceptions extends RuntimeException {

    private static final long serialVersionUID = -218662689857962021L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityConstrainsExceptions(String message) {
        super("[EntityConstrainsExceptions] " + message);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EntityConstrainsExceptions() {
        super("[EntityConstrainsExceptions]");
    }
}
