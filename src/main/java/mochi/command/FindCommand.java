package mochi.command;

import mochi.TaskList;
import mochi.ui.Ui;

/**
 * Finds tasks whose description contains the given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a find command that searches for the given keyword.
     *
     * @param keyword the keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showMatchingTasks(tasks.findByKeyword(keyword));
    }
}
