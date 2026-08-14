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
}
