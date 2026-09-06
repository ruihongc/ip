package mochi;

import mochi.command.AddDeadlineCommand;
import mochi.command.AddEventCommand;
import mochi.command.AddNoteCommand;
import mochi.command.AddTodoCommand;
import mochi.command.Command;
import mochi.command.DeleteCommand;
import mochi.command.DeleteNoteCommand;
import mochi.command.ExitCommand;
import mochi.command.FindCommand;
import mochi.command.FindNoteCommand;
import mochi.command.ListCommand;
import mochi.command.ListNotesCommand;
import mochi.command.MarkCommand;
import mochi.command.UnmarkCommand;

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
        int spaceIndex = fullCommand.indexOf(' ');
        String verb = spaceIndex == -1 ? fullCommand : fullCommand.substring(0, spaceIndex);
        String args = spaceIndex == -1 ? "" : fullCommand.substring(spaceIndex + 1);

        switch (verb) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(toZeroBasedIndex(args));
            case "unmark":
                return new UnmarkCommand(toZeroBasedIndex(args));
            case "delete":
                return new DeleteCommand(toZeroBasedIndex(args));
            case "todo":
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
                return new DeleteNoteCommand(toZeroBasedIndex(args));
            case "find-note":
                if (args.isEmpty()) {
                    throw new MochiException("Please give a keyword to search for, e.g., find-note movie");
                }
                return new FindNoteCommand(args);
            default:
                throw new MochiException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static int parseTaskNumber(String input) throws MochiException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new MochiException("Please give a task number, e.g., mark 2");
        }
    }

    /**
     * Converts a 1-based task number typed by the user into a 0-based index
     * used internally by the task list.
     *
     * @param args the task number as typed by the user
     * @return the corresponding 0-based index
     * @throws MochiException if the input is not a valid task number
     */
    private static int toZeroBasedIndex(String args) throws MochiException {
        return parseTaskNumber(args) - 1;
    }

    private static Command parseDeadline(String args) throws MochiException {
        String[] parts = args.split(" /by ", 2);
        if (parts[0].isEmpty()) {
            throw new MochiException("The description of a deadline cannot be empty.");
        }
        if (parts.length < 2) {
            throw new MochiException("Please add the deadline with /by, e.g., deadline return book /by Sunday");
        }
        return new AddDeadlineCommand(parts[0], parts[1]);
    }

    private static Command parseEvent(String args) throws MochiException {
        String[] fromParts = args.split(" /from ", 2);
        if (fromParts[0].isEmpty()) {
            throw new MochiException("The description of an event cannot be empty.");
        }
        if (fromParts.length < 2) {
            throw new MochiException("Please add the start time with /from, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2) {
            throw new MochiException("Please add the end time with /to, "
                    + "e.g., event project meeting /from Mon 2pm /to 4pm");
        }
        return new AddEventCommand(fromParts[0], toParts[0], toParts[1]);
    }
}
