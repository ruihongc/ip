package mochi;

import java.util.ArrayList;
import java.util.List;

import mochi.task.Task;

/**
 * Manages the list of tasks and automatically saves changes to disk.
 */
public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Returns the underlying list for display and iteration.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    public void add(Task task) {
        tasks.add(task);
        storage.saveTasks(tasks);
    }

    public Task remove(int index) {
        Task removed = tasks.remove(index);
        storage.saveTasks(tasks);
        return removed;
    }

    public void mark(int index) {
        tasks.get(index).markAsDone();
        storage.saveTasks(tasks);
    }

    public void unmark(int index) {
        tasks.get(index).markAsNotDone();
        storage.saveTasks(tasks);
    }
}
