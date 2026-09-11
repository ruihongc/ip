package mochi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Todo task type.
 */
public class TodoTest {

    @Test
    public void newTodo_notDone_displaysOpenCheckbox() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void markAsDone_displaysCross() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void toFileString_notDone_usesZeroFlag() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileString());
    }

    @Test
    public void toFileString_done_usesOneFlag() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("T | 1 | read book", todo.toFileString());
    }
}
