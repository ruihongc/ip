package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for the NoteList, including its automatic persistence.
 */
public class NoteListTest {

    @TempDir
    public Path tempDir;

    private NoteList newNoteList() {
        return new NoteList(new Storage(tempDir.resolve("notes.txt").toString()));
    }

    @Test
    public void add_persistsNotes() {
        NoteList notes = newNoteList();
        notes.add("first note", "second note");
        Storage storage = new Storage(tempDir.resolve("notes.txt").toString());
        assertEquals(List.of("first note", "second note"), storage.loadNotes());
    }

    @Test
    public void remove_persistsRemainingAndReturnsRemoved() {
        NoteList notes = newNoteList();
        notes.add("first note", "second note", "third note");
        String removed = notes.remove(1);
        assertEquals("second note", removed);
        Storage storage = new Storage(tempDir.resolve("notes.txt").toString());
        assertEquals(List.of("first note", "third note"), storage.loadNotes());
    }

    @Test
    public void requireValidIndex_outOfRange_throwsMochiException() {
        NoteList notes = newNoteList();
        notes.add("only note");
        MochiException ex = assertThrows(MochiException.class, () -> notes.requireValidIndex(1));
        assertEquals("There is no note number 2 in the list.", ex.getMessage());
    }

    @Test
    public void findByKeyword_returnsMatchesInOrder() {
        NoteList notes = newNoteList();
        notes.add("buy milk", "watch movie", "drink milk");
        assertEquals(List.of("buy milk", "drink milk"), notes.findByKeyword("milk"));
    }
}
