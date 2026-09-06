package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Represents a command that the user can execute.
 * Each concrete command knows how to carry itself out.
 */
public abstract class Command {

    /**
     * Executes this command on the given task list, note list, and UI.
     *
     * @param tasks the task list to operate on
     * @param notes the note list to operate on
     * @param ui    the UI for displaying output
     * @throws MochiException if the command cannot be executed
     */
    public abstract void execute(TaskList tasks, NoteList notes, Ui ui) throws MochiException;

    /**
     * Returns whether this command causes the chatbot to exit.
     */
    public boolean isExit() {
        return false;
    }
}
