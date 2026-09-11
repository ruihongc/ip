package mochi;

import mochi.command.AddDeadlineCommand;
import mochi.command.AddEventCommand;
import mochi.command.AddNoteCommand;
import mochi.command.AddPlaceCommand;
import mochi.command.AddTodoCommand;
import mochi.command.Command;
import mochi.command.DeleteCommand;
import mochi.command.DeleteNoteCommand;
import mochi.command.DeletePlaceCommand;
import mochi.command.ExitCommand;
import mochi.command.FindCommand;
import mochi.command.FindNoteCommand;
import mochi.command.FindPlaceCommand;
import mochi.command.ListCommand;
import mochi.command.ListNotesCommand;
import mochi.command.ListPlacesCommand;
import mochi.command.MarkCommand;
import mochi.command.UnmarkCommand;
import mochi.command.ViewPlaceCommand;

/**
 * Parses user input into a {@link Command} object.
 */
public class Parser {

    /**
     * Parses the given user input and returns the corresponding command.
     *
     * @param fullCommand the raw user input
     * @return the parsed command
     * @throws MochiException if the input is invalid or unrecognised
     */
    public static Command parse(String fullCommand) throws MochiException {
        String command = fullCommand.trim();
        if (command.isEmpty()) {
            throw new MochiException("The command cannot be empty.");
        }
        int spaceIndex = command.indexOf(' ');
        String verb = spaceIndex == -1 ? command : command.substring(0, spaceIndex);
        String args = spaceIndex == -1 ? "" : collapseSpaces(command.substring(spaceIndex + 1));

        switch (verb) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(toZeroBasedIndex(args, "task", "mark 2"));
            case "unmark":
                return new UnmarkCommand(toZeroBasedIndex(args, "task", "unmark 1"));
            case "delete":
                return new DeleteCommand(toZeroBasedIndex(args, "task", "delete 3"));
            case "todo":
                if (args.isEmpty()) {
                    throw new MochiException("The description of a todo cannot be empty.");
                }
                return new AddTodoCommand(args);
            case "deadline":
                return parseDeadline(args);
            case "event":
                return parseEvent(args);
            case "find":
                if (args.isEmpty()) {
                    throw new MochiException("Please give a keyword to search for, e.g., find book");
                }
                return new FindCommand(args);
            case "note":
                if (args.isEmpty()) {
                    throw new MochiException("Please give a note to record, e.g., note my waist size is 32");
                }
                return new AddNoteCommand(args);
            case "notes":
                return new ListNotesCommand();
            case "delete-note":
                return new DeleteNoteCommand(toZeroBasedIndex(args, "note", "delete-note 2"));
            case "find-note":
                if (args.isEmpty()) {
                    throw new MochiException("Please give a keyword to search for, e.g., find-note movie");
                }
                return new FindNoteCommand(args);
            case "place":
                return parsePlace(args);
            case "places":
                return new ListPlacesCommand();
            case "view-place":
                return new ViewPlaceCommand(toZeroBasedIndex(args, "place", "view-place 2"));
            case "delete-place":
                return new DeletePlaceCommand(toZeroBasedIndex(args, "place", "delete-place 2"));
            case "find-place":
                if (args.isEmpty()) {
                    throw new MochiException("Please give a keyword to search for, e.g., find-place cafe");
                }
                return new FindPlaceCommand(args);
            default:
                throw new MochiException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Collapses runs of whitespace in the given text into single spaces.
     *
     * @param input the raw text to normalise
     * @return the text with single spaces and no leading or trailing whitespace
     */
    private static String collapseSpaces(String input) {
        return input.replaceAll("\\s+", " ").trim();
    }

    /**
     * Converts a 1-based number typed by the user into a 0-based index used
     * internally by the lists, with an error message tailored to the list being
     * addressed.
     *
     * @param args    the number as typed by the user
     * @param noun    the kind of item being indexed, e.g., "task"
     * @param example an example command showing the correct usage
     * @return the corresponding 0-based index
     * @throws MochiException if the input is not a single valid number
     */
    private static int toZeroBasedIndex(String args, String noun, String example) throws MochiException {
        try {
            return Integer.parseInt(args.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new MochiException("Please give a " + noun + " number, e.g., " + example);
        }
    }

    private static Command parsePlace(String args) throws MochiException {
        String[] parts = args.split(" /d ", 2);
        String name = parts[0].trim();
        if (name.isEmpty()) {
            throw new MochiException("The name of a place cannot be empty.");
        }
        if (parts.length < 2) {
            throw new MochiException("Please add a detail for the place with /d, "
                    + "e.g., place hawkerlicious /d the hokkien mee is great");
        }
        if (parts[1].contains(" /d ")) {
            throw new MochiException("Please give the detail only once, "
                    + "e.g., place hawkerlicious /d the hokkien mee is great");
        }
        String detail = parts[1].trim();
        if (detail.isEmpty()) {
            throw new MochiException("The detail of a place cannot be empty.");
        }
        return new AddPlaceCommand(name, detail);
    }

    private static Command parseDeadline(String args) throws MochiException {
        String[] parts = args.split(" /by ", 2);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new MochiException("The description of a deadline cannot be empty.");
        }
        if (parts.length < 2) {
            throw new MochiException("Please add the deadline with /by, e.g., deadline return book /by Sunday");
        }
        if (parts[1].contains(" /by ")) {
            throw new MochiException("Please give the deadline date only once, "
                    + "e.g., deadline return book /by 2019-12-02");
        }
        String by = parts[1].trim();
        if (by.isEmpty()) {
            throw new MochiException("The deadline date cannot be empty.");
        }
        return new AddDeadlineCommand(description, by);
    }

    private static Command parseEvent(String args) throws MochiException {
        String[] fromParts = args.split(" /from ", 2);
        String description = fromParts[0].trim();
        if (description.isEmpty()) {
            throw new MochiException("The description of an event cannot be empty.");
        }
        if (fromParts.length < 2) {
            throw new MochiException("Please add the start time with /from, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        if (fromParts[1].contains(" /from ")) {
            throw new MochiException("Please give the start time only once, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        String[] toParts = fromParts[1].split(" /to ", 2);
        String from = toParts[0].trim();
        if (toParts.length < 2) {
            throw new MochiException("Please add the end time with /to, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        if (toParts[1].contains(" /to ")) {
            throw new MochiException("Please give the end time only once, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        String to = toParts[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new MochiException("The start and end times of an event cannot be empty.");
        }
        return new AddEventCommand(description, from, to);
    }
}
