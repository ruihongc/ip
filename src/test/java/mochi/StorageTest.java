package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mochi.place.Place;
import mochi.task.Deadline;
import mochi.task.Task;
import mochi.task.Todo;

/**
 * Tests for file-based persistence of tasks, notes, and places.
 */
public class StorageTest {

    @TempDir
    public Path tempDir;

    @Test
    public void tasks_roundTrip() {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        storage.saveTasks(List.of(new Todo("read book"), new Deadline("return book", LocalDate.of(2019, 12, 2))));
        List<Task> loaded = storage.loadTasks();
        assertEquals(2, loaded.size());
        assertEquals("[T][ ] read book", loaded.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 2 2019)", loaded.get(1).toString());
    }

    @Test
    public void notes_roundTrip() {
        Storage storage = new Storage(tempDir.resolve("notes.txt").toString());
        storage.saveNotes(List.of("first note", "second note"));
        assertEquals(List.of("first note", "second note"), storage.loadNotes());
    }

    @Test
    public void places_roundTrip() {
        Storage storage = new Storage(tempDir.resolve("places.txt").toString());
        storage.savePlaces(List.of(new Place("cafes", "good wifi")));
        List<Place> loaded = storage.loadPlaces();
        assertEquals(1, loaded.size());
        assertEquals("cafes", loaded.get(0).getName());
    }

    @Test
    public void loadTasks_missingFile_returnsEmptyAndCreatesFile() {
        Path path = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(path.toString());
        assertEquals(0, storage.loadTasks().size());
        assertTrue(Files.exists(path));
    }

    @Test
    public void loadTasks_skipsCorruptedLines() throws IOException {
        Path path = tempDir.resolve("tasks.txt");
        Files.write(path, List.of("T | 0 | read book", "not a task", "D | 0 | broken"));
        Storage storage = new Storage(path.toString());
        List<Task> loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertEquals("[T][ ] read book", loaded.get(0).toString());
    }

    @Test
    public void loadPlaces_skipsCorruptedLines() throws IOException {
        Path path = tempDir.resolve("places.txt");
        Files.write(path, List.of("cafes | good wifi", "corrupted-place-line"));
        Storage storage = new Storage(path.toString());
        List<Place> loaded = storage.loadPlaces();
        assertEquals(1, loaded.size());
        assertEquals("cafes", loaded.get(0).getName());
    }
}
