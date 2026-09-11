package mochi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Deadline task type.
 */
public class DeadlineTest {

    @Test
    public void toString_formatsDueDate() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        assertEquals("[D][ ] return book (by: Dec 2 2019)", deadline.toString());
    }

    @Test
    public void toFileString_roundTrips() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        assertEquals("D | 0 | return book | 2019-12-02", deadline.toFileString());
    }
}
