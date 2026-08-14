/**
 * Represents the type of a task: a todo, a deadline, or an event.
 * The symbol is the character shown in the task list (e.g., "T" for a todo),
 * and is also what a task type is stored as when saving tasks to a file.
 */
public enum TaskType {
    TODO('T'),
    DEADLINE('D'),
    EVENT('E');

    private final char symbol;

    /**
     * Creates a task type with the given display symbol.
     */
    TaskType(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol used to show this task type, e.g., 'T' for a todo.
     */
    public char getSymbol() {
        return symbol;
    }
}
