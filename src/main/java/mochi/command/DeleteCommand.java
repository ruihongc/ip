package mochi.command;

import mochi.MochiException;
import mochi.NoteList;
import mochi.TaskList;
import mochi.task.Task;
import mochi.ui.Ui;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a delete command for the task at the given 0-based index.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, NoteList notes, Ui ui) throws MochiException {
        tasks.requireValidIndex(index);
        Task removed = tasks.remove(index);
        ui.showDeleted(removed, tasks.getTasks().size());
    }
}
