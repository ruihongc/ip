package mochi;

/**
 * Represents an error caused by an invalid command entered by the user.
 */
public class MochiException extends Exception {
    public MochiException(String message) {
        super(message);
    }
}
