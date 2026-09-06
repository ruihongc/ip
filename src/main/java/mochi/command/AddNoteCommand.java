package mochi.command;

import mochi.NoteList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Adds a note to the note list.
 */
public class AddNoteCommand extends Command {
    private final String note;

    /**
     * Creates an add-note command with the given note text.
     *
     * @param note the text to record as a note
     */
    public AddNoteCommand(String note) {
        this.note = note;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, Ui ui) {
        notes.add(note);
        ui.showAddedNote(note, notes.getNotes().size());
    }
}
