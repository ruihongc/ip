package mochi;

import java.util.ArrayList;
import java.util.List;

import mochi.place.Place;

/**
 * Manages the list of places and automatically saves changes to disk.
 */
public class PlaceList {
    private final List<Place> places;
    private final Storage storage;

    /**
     * Creates an empty place list that auto-saves to the given storage.
     *
     * @param storage the storage backend for persistence
     */
    public PlaceList(Storage storage) {
        this.places = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Returns the underlying list of places for display and iteration.
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Adds one or more places to the list and saves.
     *
     * @param places the places to add (zero or more)
     */
    public void add(Place... places) {
        for (Place place : places) {
            assert place != null : "A place to be added must not be null";
            this.places.add(place);
        }
        storage.savePlaces(this.places);
    }

    /**
     * Checks whether the list already contains a place with the given name,
     * ignoring case.
     *
     * @param name the name to look for
     * @return true if a place with the name already exists
     */
    public boolean containsName(String name) {
        return places.stream()
                .anyMatch(place -> place.getName().equalsIgnoreCase(name));
    }

    /**
     * Removes the place at the given index and saves.
     *
     * @param index 0-based position of the place to remove
     * @return the removed place
     */
    public Place remove(int index) {
        assert index >= 0 && index < places.size() : "Callers must validate the index before removing";
        Place removed = places.remove(index);
        storage.savePlaces(places);
        return removed;
    }

    /**
     * Checks that the given 0-based index refers to a place in the list.
     *
     * @param index 0-based position of the place
     * @throws MochiException if the index is out of range
     */
    public void requireValidIndex(int index) throws MochiException {
        if (index < 0 || index >= places.size()) {
            throw new MochiException("There is no place number " + (index + 1) + " in the list.");
        }
    }

    /**
     * Returns the places whose name or details contain the given keyword.
     *
     * @param keyword the keyword to search for
     * @return the matching places in their current order
     */
    public List<Place> findByKeyword(String keyword) {
        return places.stream()
                .filter(place -> place.contains(keyword))
                .toList();
    }
}
