import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that starts and ends at specific date/times.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates an event with the given description and start and end dates.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(TaskType.EVENT, description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event,
     * e.g., "[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)".
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)) + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + from.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + " | " + to.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
