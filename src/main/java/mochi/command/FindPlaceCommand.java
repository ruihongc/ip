package mochi.command;

import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Finds places whose name or details contain the given keyword.
 */
public class FindPlaceCommand extends Command {
    private final String keyword;

    /**
     * Creates a find-place command that searches for the given keyword.
     *
     * @param keyword the keyword to search for in place names and details
     */
    public FindPlaceCommand(String keyword) {
        this.keyword = keyword;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) {
        ui.showMatchingPlaces(places.findByKeyword(keyword));
    }
}
