package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mochi.task.Task;
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

    @Test
    public void markAndUnmark_flipsDisplayAndFlag() {
        TaskList taskList = new TaskList(new Storage(tempDir.resolve("tasks.txt").toString()));
        taskList.add(new Todo("read"));
        taskList.mark(0);
        assertEquals("[T][X] read", taskList.getTasks().get(0).toString());
        taskList.unmark(0);
        assertEquals("[T][ ] read", taskList.getTasks().get(0).toString());
    }

    @Test
    public void remove_removesAndReturnsTask() {
        TaskList taskList = new TaskList(new Storage(tempDir.resolve("tasks.txt").toString()));
        taskList.add(new Todo("read"));
        Task removed = taskList.remove(0);
        assertEquals("[T][ ] read", removed.toString());
        assertEquals(0, taskList.getTasks().size());
    }

    @Test
    public void requireValidIndex_outOfRange_throwsMochiException() {
        TaskList taskList = new TaskList(new Storage(tempDir.resolve("tasks.txt").toString()));
        taskList.add(new Todo("read"));
        MochiException ex = assertThrows(MochiException.class, () -> taskList.requireValidIndex(2));
        assertEquals("There is no task number 3 in the list.", ex.getMessage());
    }

    @Test
    public void findByKeyword_matchesDisplayText() {
        TaskList taskList = new TaskList(new Storage(tempDir.resolve("tasks.txt").toString()));
        taskList.add(new Todo("read book"), new Todo("write essay"));
        assertEquals(1, taskList.findByKeyword("book").size());
        assertEquals(0, taskList.findByKeyword("swim").size());
    }
}
