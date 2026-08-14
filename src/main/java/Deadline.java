/**
 * Represents a task that must be done before a specific date/time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a deadline with the given description and due time.
     */
    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline, e.g., "[D][ ] return book (by: Sunday)".
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString() + " (by: " + by + ")";
    }
}
