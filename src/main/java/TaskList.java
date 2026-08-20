import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks and automatically saves changes to disk after
 * every add, mark, unmark, or delete operation.
 */
public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    /**
     * Creates an empty task list backed by the given storage.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Returns the underlying list so that Mochi can iterate over it for display.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list and saves.
     */
    public void add(Task task) {
        tasks.add(task);
        storage.saveTasks(tasks);
    }

    /**
     * Removes the task at the given index and saves.
     *
     * @param index 0-based position in the list
     * @return the removed task
     */
    public Task remove(int index) {
        Task removed = tasks.remove(index);
        storage.saveTasks(tasks);
        return removed;
    }

    /**
     * Marks the task at the given index as done and saves.
     */
    public void mark(int index) {
        tasks.get(index).markAsDone();
        storage.saveTasks(tasks);
    }

    /**
     * Marks the task at the given index as not done and saves.
     */
    public void unmark(int index) {
        tasks.get(index).markAsNotDone();
        storage.saveTasks(tasks);
    }
}
