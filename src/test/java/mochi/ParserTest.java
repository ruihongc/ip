package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
import mochi.command.FindNoteCommand;
import mochi.command.FindPlaceCommand;
import mochi.command.ListCommand;
import mochi.command.ListNotesCommand;
import mochi.command.ListPlacesCommand;
import mochi.command.MarkCommand;
import mochi.command.UnmarkCommand;
import mochi.command.ViewPlaceCommand;

public class ParserTest {

    @Test
    public void parse_bye_returnsExitCommand() throws MochiException {
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
    }

    @Test
    public void parse_list_returnsListCommand() throws MochiException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
    }

    @Test
    public void parse_mark_returnsMarkCommand() throws MochiException {
        Command cmd = Parser.parse("mark 2");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    public void parse_unmark_returnsUnmarkCommand() throws MochiException {
        Command cmd = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, cmd);
    }

    @Test
    public void parse_delete_returnsDeleteCommand() throws MochiException {
        Command cmd = Parser.parse("delete 3");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    public void parse_todo_returnsAddTodoCommand() throws MochiException {
        Command cmd = Parser.parse("todo read book");
        assertInstanceOf(AddTodoCommand.class, cmd);
    }

    @Test
    public void parse_deadline_returnsAddDeadlineCommand() throws MochiException {
        Command cmd = Parser.parse("deadline return book /by 2019-12-02");
        assertInstanceOf(AddDeadlineCommand.class, cmd);
    }

    @Test
    public void parse_event_returnsAddEventCommand() throws MochiException {
        Command cmd = Parser.parse("event meeting /from 2019-10-15 /to 2019-10-16");
        assertInstanceOf(AddEventCommand.class, cmd);
    }

    @Test
    public void parse_unknownCommand_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("blah"));
        assertEquals("I'm sorry, but I don't know what that means :-(", ex.getMessage());
    }

    @Test
    public void parse_markNonNumeric_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("mark abc"));
        assertEquals("Please give a task number, e.g., mark 2", ex.getMessage());
    }

    @Test
    public void parse_markNoArgs_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("mark"));
        assertEquals("Please give a task number, e.g., mark 2", ex.getMessage());
    }

    @Test
    public void parse_todoEmpty_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("todo"));
        assertEquals("The description of a todo cannot be empty.", ex.getMessage());
    }

    @Test
    public void parse_deadlineMissingBy_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("deadline return book"));
        assertEquals("Please add the deadline with /by, e.g., deadline return book /by Sunday",
                ex.getMessage());
    }

    @Test
    public void parse_eventMissingFrom_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("event meeting"));
        assertEquals("Please add the start time with /from, e.g., event project meeting /from Mon 2pm /to 4pm",
                ex.getMessage());
    }

    @Test
    public void parse_eventMissingTo_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("event meeting /from 2019-10-15"));
        assertEquals("Please add the end time with /to, e.g., event project meeting /from Mon 2pm /to 4pm",
                ex.getMessage());
    }

    @Test
    public void parse_note_returnsAddNoteCommand() throws MochiException {
        assertInstanceOf(AddNoteCommand.class, Parser.parse("note buy milk"));
    }

    @Test
    public void parse_notes_returnsListNotesCommand() throws MochiException {
        assertInstanceOf(ListNotesCommand.class, Parser.parse("notes"));
    }

    @Test
    public void parse_deleteNote_returnsDeleteNoteCommand() throws MochiException {
        assertInstanceOf(DeleteNoteCommand.class, Parser.parse("delete-note 2"));
    }

    @Test
    public void parse_findNote_returnsFindNoteCommand() throws MochiException {
        assertInstanceOf(FindNoteCommand.class, Parser.parse("find-note movie"));
    }

    @Test
    public void parse_place_returnsAddPlaceCommand() throws MochiException {
        assertInstanceOf(AddPlaceCommand.class, Parser.parse("place cafes /d good wifi"));
    }

    @Test
    public void parse_places_returnsListPlacesCommand() throws MochiException {
        assertInstanceOf(ListPlacesCommand.class, Parser.parse("places"));
    }

    @Test
    public void parse_viewPlace_returnsViewPlaceCommand() throws MochiException {
        assertInstanceOf(ViewPlaceCommand.class, Parser.parse("view-place 3"));
    }

    @Test
    public void parse_deletePlace_returnsDeletePlaceCommand() throws MochiException {
        assertInstanceOf(DeletePlaceCommand.class, Parser.parse("delete-place 1"));
    }

    @Test
    public void parse_findPlace_returnsFindPlaceCommand() throws MochiException {
        assertInstanceOf(FindPlaceCommand.class, Parser.parse("find-place cafe"));
    }

    @Test
    public void parse_surroundingSpaces_ignored() throws MochiException {
        assertInstanceOf(ListCommand.class, Parser.parse("  list  "));
    }

    @Test
    public void parse_emptyCommand_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("   "));
        assertEquals("The command cannot be empty.", ex.getMessage());
    }

    @Test
    public void parse_markExtraArgs_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("mark 2 3"));
        assertEquals("Please give a task number, e.g., mark 2", ex.getMessage());
    }

    @Test
    public void parse_deleteNoteNoArgs_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("delete-note"));
        assertEquals("Please give a note number, e.g., delete-note 2", ex.getMessage());
    }

    @Test
    public void parse_viewPlaceNoArgs_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("view-place"));
        assertEquals("Please give a place number, e.g., view-place 2", ex.getMessage());
    }

    @Test
    public void parse_placeNoDetail_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("place cafes"));
        assertEquals("Please add a detail for the place with /d, "
                + "e.g., place hawkerlicious /d the hokkien mee is great", ex.getMessage());
    }

    @Test
    public void parse_placeDuplicateDetail_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("place cafes /d wifi /d tables"));
        assertEquals("Please give the detail only once, "
                + "e.g., place hawkerlicious /d the hokkien mee is great", ex.getMessage());
    }

    @Test
    public void parse_deadlineDuplicateBy_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser
                .parse("deadline x /by 2019-01-01 /by 2019-02-02"));
        assertEquals("Please give the deadline date only once, e.g., deadline return book /by 2019-12-02",
                ex.getMessage());
    }

    @Test
    public void parse_deadlineEmpty_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser.parse("deadline"));
        assertEquals("The description of a deadline cannot be empty.", ex.getMessage());
    }

    @Test
    public void parse_eventDuplicateFrom_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser
                .parse("event x /from 2019-01-01 /from 2019-01-02 /to 2019-01-03"));
        assertEquals("Please give the start time only once, e.g., event project meeting /from Mon 2pm /to 4pm",
                ex.getMessage());
    }

    @Test
    public void parse_eventDuplicateTo_throwsMochiException() {
        MochiException ex = assertThrows(MochiException.class, () -> Parser
                .parse("event x /from 2019-01-01 /to 2019-01-02 /to 2019-01-03"));
        assertEquals("Please give the end time only once, e.g., event project meeting /from Mon 2pm /to 4pm",
                ex.getMessage());
    }
}
