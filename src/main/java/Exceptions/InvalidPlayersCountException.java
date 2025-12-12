package Exceptions;

/**
 * An unchecked exception thrown when a game operation or event requires a specific
 * number of players (typically 2 or more) to proceed, but the current number of
 * active players does not meet that requirement.
 * This is a {@code RuntimeException}, meaning it does not need to be explicitly
 * caught in all calling methods.
 */
public class InvalidPlayersCountException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public InvalidPlayersCountException(String message) {
        super(message);
    }
}