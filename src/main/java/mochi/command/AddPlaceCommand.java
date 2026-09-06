package mochi.command;

import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.place.Place;
import mochi.ui.Ui;

/**
 * Adds a place to the place list.
 */
public class AddPlaceCommand extends Command {
    private final String name;
    private final String detail;

    /**
     * Creates an add-place command with the given name and detail.
     *
     * @param name   the name of the place
     * @param detail a detail to record about the place
     */
    public AddPlaceCommand(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) {
        Place place = new Place(name, detail);
        places.add(place);
        ui.showAddedPlace(place, places.getPlaces().size());
    }
}
