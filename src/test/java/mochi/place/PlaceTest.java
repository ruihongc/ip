package mochi.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import mochi.MochiException;

/**
 * Tests for the Place class.
 */
public class PlaceTest {

    @Test
    public void newPlace_holdsNameAndOneDetail() {
        Place place = new Place("cafes", "good wifi");
        assertEquals("cafes", place.getName());
        assertEquals(List.of("good wifi"), place.getDetails());
        assertEquals("cafes", place.toString());
    }

    @Test
    public void fromFileString_multipleDetails_parsesAll() throws MochiException {
        Place place = Place.fromFileString("hawkerlicious | best hokkien mee | 10am-8pm");
        assertEquals("hawkerlicious", place.getName());
        assertEquals(List.of("best hokkien mee", "10am-8pm"), place.getDetails());
    }

    @Test
    public void contains_matchesNameOrDetail() throws MochiException {
        Place place = Place.fromFileString("hawkerlicious | best hokkien mee");
        assertTrue(place.contains("hawker"));
        assertTrue(place.contains("mee"));
        assertFalse(place.contains("laksa"));
    }

    @Test
    public void toFileString_roundTrips() throws MochiException {
        String line = "hawkerlicious | best hokkien mee | 10am-8pm";
        assertEquals(line, Place.fromFileString(line).toFileString());
    }

    @Test
    public void fromFileString_tooFewParts_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Place.fromFileString("no detail here"));
        assertEquals("Corrupted place data.", ex.getMessage());
    }

    @Test
    public void fromFileString_emptyName_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Place.fromFileString(" | not a name"));
        assertEquals("Corrupted place data.", ex.getMessage());
    }
}
