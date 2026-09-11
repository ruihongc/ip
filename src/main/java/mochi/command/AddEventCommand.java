package mochi.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import mochi.MochiException;
import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.task.Event;
import mochi.ui.Ui;

/**
 * Adds an event task to the task list.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String fromString;
    private final String toString;

    /**
     * Creates an add-event command with the given description and date strings.
     *
     * @param description the task description
     * @param fromString  the start date in yyyy-mm-dd format
     * @param toString    the end date in yyyy-mm-dd format
     */
    public AddEventCommand(String description, String fromString, String toString) {
        this.description = description;
        this.fromString = fromString;
        this.toString = toString;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) throws MochiException {
        if (description.isEmpty()) {
            throw new MochiException("The description of an event cannot be empty.");
        }
        LocalDate from;
        LocalDate to;
        try {
            from = LocalDate.parse(fromString.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            to = LocalDate.parse(toString.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new MochiException("The dates must be in yyyy-mm-dd format, e.g., 2019-10-15");
        }
        if (to.isBefore(from)) {
            throw new MochiException("The end date of an event cannot be before its start date.");
        }
        Event event = new Event(description, from, to);
        tasks.add(event);
        ui.showAdded(event, tasks.getTasks().size());
    }
}
