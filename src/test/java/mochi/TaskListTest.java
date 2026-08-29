package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mochi.task.Todo;

/**
 * Tests for the TaskList, including its varargs add method.
 */
public class TaskListTest {

    @TempDir
    public Path tempDir;

    @Test
    public void add_varargsOverload_addsAllTasks() {
        TaskList taskList = new TaskList(new Storage(tempDir.resolve("tasks.txt").toString()));
        taskList.add(new Todo("read"), new Todo("write"), new Todo("run"));
        assertEquals(3, taskList.getTasks().size());
    }
}
