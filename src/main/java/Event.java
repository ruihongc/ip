/**
 * Represents a task that starts and ends at specific date/times.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event with the given description and start and end times.
     */
    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event,
     * e.g., "[E][ ] project meeting (from: Mon 2pm to: 4pm)".
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
