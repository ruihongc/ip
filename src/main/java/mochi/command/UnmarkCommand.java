package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.PlaceList;
import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Marks a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an unmark command for the task at the given 0-based index.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, PlaceList places, Ui ui) throws MochiException {
        tasks.requireValidIndex(index);
        tasks.unmark(index);
        ui.showUnmarked(tasks.getTasks().get(index));
    }
}
