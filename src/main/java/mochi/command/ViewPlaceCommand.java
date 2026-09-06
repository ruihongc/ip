package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.place.Place;
import mochi.ui.Ui;

/**
 * Shows the details of a place in the place list.
 */
public class ViewPlaceCommand extends Command {
    private final int index;

    /**
     * Creates a view-place command for the place at the given 0-based index.
     *
     * @param index 0-based position of the place to view
     */
    public ViewPlaceCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) throws MochiException {
        places.requireValidIndex(index);
        Place place = places.getPlaces().get(index);
        ui.showPlaceDetails(place);
    }
}
