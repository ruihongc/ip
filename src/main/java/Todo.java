/**
 * Represents a task without any date/time attached to it.
 */
public class Todo extends Task {
    /**
     * Creates a todo with the given description.
     */
    public Todo(String description) {
        super(TaskType.TODO, description);
    }

    /**
     * Returns a string representation of the todo, e.g., "[T][ ] read book".
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString();
    }
}
