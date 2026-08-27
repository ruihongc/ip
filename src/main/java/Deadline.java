import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that must be done before a specific date/time.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Creates a deadline with the given description and due date.
     */
    public Deadline(String description, LocalDate by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline,
     * e.g., "[D][ ] return book (by: Dec 02 2019)".
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString()
                + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + by.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
