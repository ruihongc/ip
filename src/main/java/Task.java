/**
 * Represents a task with a type, description, and done status.
 * A deadline also has a "by" time, and an event has "from" and "to" times.
 */
public class Task {
    protected char type;
    protected String description;
    protected boolean isDone;
    protected String by;
    protected String from;
    protected String to;

    /**
     * Creates a todo with the given description that is not done yet.
     */
    public Task(String description) {
        this('T', description, null, null, null);
    }

    /**
     * Creates a task of the given type with the given description and,
     * if applicable, its by/from/to times.
     */
    public Task(char type, String description, String by, String from, String to) {
        this.type = type;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
        this.isDone = false;
    }

    /**
     * Returns "X" if the task is done, or " " if it is not done yet.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, e.g., "[T][ ] read book".
     */
    @Override
    public String toString() {
        String extra = "";
        if (type == 'D') {
            extra = " (by: " + by + ")";
        } else if (type == 'E') {
            extra = " (from: " + from + " to: " + to + ")";
        }
        return "[" + type + "][" + getStatusIcon() + "] " + description + extra;
    }
}
