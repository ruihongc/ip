package mochi.command;

import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Lists all notes in the note list.
 */
public class ListNotesCommand extends Command {
    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) {
        ui.showNoteList(notes.getNotes());
    }
}
