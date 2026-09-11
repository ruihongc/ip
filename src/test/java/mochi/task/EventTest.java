package mochi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Event task type.
 */
public class EventTest {

    @Test
    public void toString_formatsDateRange() {
        Event event = new Event("meeting", LocalDate.of(2019, 10, 15), LocalDate.of(2019, 10, 16));
        assertEquals("[E][ ] meeting (from: Oct 15 2019 to: Oct 16 2019)", event.toString());
    }

    @Test
    public void toFileString_roundTrips() {
        Event event = new Event("meeting", LocalDate.of(2019, 10, 15), LocalDate.of(2019, 10, 16));
        assertEquals("E | 0 | meeting | 2019-10-15 | 2019-10-16", event.toFileString());
    }
}
