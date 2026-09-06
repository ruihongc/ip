package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Deletes a note from the note list.
 */
public class DeleteNoteCommand extends Command {
    private final int index;

    /**
     * Creates a delete-note command for the note at the given 0-based index.
     *
     * @param index 0-based position of the note to delete
     */
    public DeleteNoteCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) throws MochiException {
        notes.requireValidIndex(index);
        String removed = notes.remove(index);
        ui.showDeletedNote(removed, notes.getNotes().size());
    }
}
