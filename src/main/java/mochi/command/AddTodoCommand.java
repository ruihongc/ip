package mochi.command;

import mochi.MochiException;
import mochi.TaskList;
import mochi.task.Todo;
import mochi.ui.Ui;

/**
 * Adds a todo task to the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Creates an add-todo command with the given description.
     *
     * @param description the task description
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws MochiException {
        if (description.isEmpty()) {
            throw new MochiException("The description of a todo cannot be empty.");
        }
        Todo todo = new Todo(description);
        tasks.add(todo);
        ui.showAdded(todo, tasks.getTasks().size());
    }
}
