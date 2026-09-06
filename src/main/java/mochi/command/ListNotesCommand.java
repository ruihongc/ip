package mochi.command;

import mochi.NoteList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Lists all notes in the note list.
 */
public class ListNotesCommand extends Command {
    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, Ui ui) {
        ui.showNoteList(notes.getNotes());
    }
}
