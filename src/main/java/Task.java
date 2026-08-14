/**
 * Represents a task with a description and a done status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description that is not done yet.
     */
    public Task(String description) {
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
     * Returns a string representation of the task, e.g., "[X] read book".
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
