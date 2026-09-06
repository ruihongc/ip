package mochi.command;

import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Lists all places in the place list.
 */
public class ListPlacesCommand extends Command {
    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) {
        ui.showPlaceList(places.getPlaces());
    }
}
