package mochi;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of notes and automatically saves changes to disk.
 */
public class NoteList {
    private final List<String> notes;
    private final Storage storage;

    /**
     * Creates an empty note list that auto-saves to the given storage.
     *
     * @param storage the storage backend for persistence
     */
    public NoteList(Storage storage) {
        this.notes = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Returns the underlying list of notes for display and iteration.
     */
    public List<String> getNotes() {
        return notes;
    }

    /**
     * Adds one or more notes to the list and saves.
     *
     * @param notes the notes to add (zero or more)
     */
    public void add(String... notes) {
        for (String note : notes) {
            assert note != null : "A note to be added must not be null";
            this.notes.add(note);
        }
        storage.saveNotes(this.notes);
    }

    /**
     * Removes the note at the given index and saves.
     *
     * @param index 0-based position of the note to remove
     * @return the removed note
     */
    public String remove(int index) {
        assert index >= 0 && index < notes.size() : "Callers must validate the index before removing";
        String removed = notes.remove(index);
        storage.saveNotes(notes);
        return removed;
    }

    /**
     * Checks that the given 0-based index refers to a note in the list.
     *
     * @param index 0-based position of the note
     * @throws MochiException if the index is out of range
     */
    public void requireValidIndex(int index) throws MochiException {
        if (index < 0 || index >= notes.size()) {
            throw new MochiException("There is no note number " + (index + 1) + " in the list.");
        }
    }

    /**
     * Returns the notes whose text contains the given keyword.
     *
     * @param keyword the keyword to search for
     * @return the matching notes in their current order
     */
    public List<String> findByKeyword(String keyword) {
        return notes.stream()
                .filter(note -> note.contains(keyword))
                .toList();
    }
}
