package mochi.place;

import java.util.ArrayList;
import java.util.List;

import mochi.MochiException;

/**
 * Represents a place with a name and a list of details recorded about it.
 */
public class Place {
    private final String name;
    private final List<String> details;

    /**
     * Creates a place with the given name and one detail.
     *
     * @param name   the display name of the place
     * @param detail a detail to record about the place
     */
    public Place(String name, String detail) {
        assert name != null : "A place must have a name";
        assert detail != null : "A place must have a detail";
        this.name = name;
        this.details = new ArrayList<>();
        this.details.add(detail);
    }

    /**
     * Returns the display name of this place.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the details recorded about this place.
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Returns true if the place's name or any of its details contains the keyword.
     *
     * @param keyword the keyword to search for
     * @return true if there is a match, false otherwise
     */
    public boolean contains(String keyword) {
        return name.contains(keyword)
                || details.stream().anyMatch(detail -> detail.contains(keyword));
    }

    /**
     * Returns a human-readable representation of this place.
     *
     * @return the name of the place
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a pipe-delimited string for saving this place to file.
     *
     * @return file-format string representation
     */
    public String toFileString() {
        return name + " | " + String.join(" | ", details);
    }

    /**
     * Parses a line from the saved file and returns the corresponding Place.
     *
     * @param line the raw line saved to file
     * @return the parsed place
     * @throws MochiException if the line is not a valid place record
     */
    public static Place fromFileString(String line) throws MochiException {
        String[] parts = line.split("\\|");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new MochiException("Corrupted place data.");
        }
        Place place = new Place(parts[0].trim(), parts[1].trim());
        for (int i = 2; i < parts.length; i++) {
            place.details.add(parts[i].trim());
        }
        return place;
    }
}
