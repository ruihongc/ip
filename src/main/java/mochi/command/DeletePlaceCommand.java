package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.place.Place;
import mochi.ui.Ui;

/**
 * Deletes a place from the place list.
 */
public class DeletePlaceCommand extends Command {
    private final int index;

    /**
     * Creates a delete-place command for the place at the given 0-based index.
     *
     * @param index 0-based position of the place to delete
     */
    public DeletePlaceCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) throws MochiException {
        places.requireValidIndex(index);
        Place removed = places.remove(index);
        ui.showDeletedPlace(removed, places.getPlaces().size());
    }
}
