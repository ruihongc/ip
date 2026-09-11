package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mochi.place.Place;

/**
 * Tests for the PlaceList, including its automatic persistence.
 */
public class PlaceListTest {

    @TempDir
    public Path tempDir;

    private PlaceList newPlaceList() {
        return new PlaceList(new Storage(tempDir.resolve("places.txt").toString()));
    }

    @Test
    public void add_persistsPlaces() {
        PlaceList places = newPlaceList();
        places.add(new Place("cafes", "good wifi"), new Place("hawkerlicious", "best hokkien mee"));
        Storage storage = new Storage(tempDir.resolve("places.txt").toString());
        List<Place> loaded = storage.loadPlaces();
        assertEquals(2, loaded.size());
        assertEquals("cafes", loaded.get(0).getName());
    }

    @Test
    public void remove_persistsRemaining() {
        PlaceList places = newPlaceList();
        places.add(new Place("cafes", "good wifi"), new Place("gym", "closes at 10pm"));
        assertEquals("gym", places.remove(1).toString());
        Storage storage = new Storage(tempDir.resolve("places.txt").toString());
        List<Place> loaded = storage.loadPlaces();
        assertEquals(1, loaded.size());
        assertEquals("cafes", loaded.get(0).getName());
    }

    @Test
    public void containsName_ignoresCase() {
        PlaceList places = newPlaceList();
        places.add(new Place("Cafes", "good wifi"));
        assertTrue(places.containsName("cafes"));
        assertFalse(places.containsName("gym"));
    }

    @Test
    public void requireValidIndex_outOfRange_throwsMochiException() {
        PlaceList places = newPlaceList();
        places.add(new Place("cafes", "good wifi"));
        MochiException ex = assertThrows(MochiException.class, () -> places.requireValidIndex(2));
        assertEquals("There is no place number 3 in the list.", ex.getMessage());
    }

    @Test
    public void findByKeyword_matchesNameOrDetail() {
        PlaceList places = newPlaceList();
        places.add(new Place("hawkerlicious", "best hokkien mee"));
        places.add(new Place("cafes", "good wifi"));
        assertEquals(1, places.findByKeyword("mee").size());
        assertEquals(1, places.findByKeyword("cafes").size());
        assertEquals(0, places.findByKeyword("laksa").size());
    }
}
