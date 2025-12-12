package Exceptions;

/**
 * An unchecked exception thrown when a method is called that is explicitly
 * not implemented or not intended to be supported by the current class implementation.
 * <p>
 * This is typically used in place of {@code UnsupportedOperationException} when
 * a custom, descriptive runtime exception is preferred.
 */
public class NotSupportedOperation extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public NotSupportedOperation(String message) {
        super(message);
    }
}