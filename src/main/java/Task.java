/**
 * Represents a task with a type, description, and done status.
 * This is the base class for all task types.
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a type, description, and done status.
 * This is the base class for all task types.
 */
public class Task {
    protected final TaskType type;
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task of the given type with the given description that is not done yet.
     */
    protected Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
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
     * Returns a string representation of the task, e.g., "[ ] read book".
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a string representation of the task suitable for saving to a file.
     * Format: TYPE | done (0 or 1) | description
     * Subclasses override this to append additional fields.
     */
    public String toFileString() {
        return type.getSymbol() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Parses a line from the saved file and returns the corresponding Task.
     * Format: TYPE | done (0 or 1) | description [| extra fields...]
     *
     * @param line a single line from the data file
     * @return the parsed Task, or {@code null} if the type is not recognised
     * @throws MochiException if the line is corrupted or missing required fields
     */
    public static Task fromFileString(String line) throws MochiException {
        String[] parts = line.split("\\|", 3);
        if (parts.length < 3) {
            throw new MochiException("Corrupted task data.");
        }

        String typeStr = parts[0].trim();
        String doneStr = parts[1].trim();
        String remaining = parts[2].trim();

        boolean isDone;
        try {
            isDone = Integer.parseInt(doneStr) == 1;
        } catch (NumberFormatException e) {
            throw new MochiException("Corrupted task data.");
        }

        switch (typeStr) {
        case "T":
            Todo todo = new Todo(remaining);
            if (isDone) {
                todo.markAsDone();
            }
            return todo;
        case "D":
            String[] deadlineParts = remaining.split("\\|", 2);
            if (deadlineParts.length < 2) {
                throw new MochiException("Corrupted deadline data.");
            }
            LocalDate deadlineDate;
            try {
                deadlineDate = LocalDate.parse(deadlineParts[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                throw new MochiException("Corrupted deadline data.");
            }
            Deadline deadline = new Deadline(deadlineParts[0].trim(), deadlineDate);
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;
        case "E":
            String[] eventParts = remaining.split("\\|", 3);
            if (eventParts.length < 3) {
                throw new MochiException("Corrupted event data.");
            }
            LocalDate eventFrom;
            LocalDate eventTo;
            try {
                eventFrom = LocalDate.parse(eventParts[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                eventTo = LocalDate.parse(eventParts[2].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                throw new MochiException("Corrupted event data.");
            }
            Event event = new Event(eventParts[0].trim(), eventFrom, eventTo);
            if (isDone) {
                event.markAsDone();
            }
            return event;
        default:
            return null;
        }
    }
}
