package mochi.task;

/**
 * Represents the type of a task: a todo, a deadline, or an event.
 */
public enum TaskType {
    TODO('T'),
    DEADLINE('D'),
    EVENT('E');

    private final char symbol;

    TaskType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
