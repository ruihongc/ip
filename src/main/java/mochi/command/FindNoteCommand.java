package mochi.command;

import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Finds notes whose text contains the given keyword.
 */
public class FindNoteCommand extends Command {
    private final String keyword;

    /**
     * Creates a find-note command that searches for the given keyword.
     *
     * @param keyword the keyword to search for in note text
     */
    public FindNoteCommand(String keyword) {
        this.keyword = keyword;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) {
        ui.showMatchingNotes(notes.findByKeyword(keyword));
    }
}
