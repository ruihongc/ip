/**
 * Represents an error caused by an invalid command entered by the user.
 */
public class MochiException extends Exception {
    /**
     * Creates an exception with a message describing what went wrong.
     */
    public MochiException(String message) {
        super(message);
    }
}
