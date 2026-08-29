package mochi.ui;

import java.io.PrintStream;
import java.util.List;

import mochi.task.Task;

/**
 * Handles all user interface operations: displaying messages and reading input.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    private final PrintStream out;

    /**
     * Creates a UI that writes to the standard console output.
     */
    public Ui() {
        this(System.out);
    }

    /**
     * Creates a UI that writes to the given output stream.
     *
     * @param out the stream that display methods write to
     */
    public Ui(PrintStream out) {
        this.out = out;
    }

    /**
     * Shows the welcome banner.
     */
    public void showWelcome() {
        String banner = "    __  ___           __    _\n"
                + "   /  |/  /___  _____/ /_  (_)\n"
                + "  / /|_/ / __ \\/ ___/ __ \\/ /\n"
                + " / /  / / /_/ / /__/ / / / /\n"
                + "/_/  /_/\\____/\\___/_/ /_/_/";
        out.println(LINE);
        out.println(banner);
        out.println("Hello! I'm Mochi.");
        out.println("What can I do for you?");
        out.println(LINE);
    }

    /**
     * Shows the divider line.
     */
    public void showLine() {
        out.println(LINE);
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        out.println(LINE);
        out.println("Bye. Hope to see you again soon!");
        out.println(LINE);
    }

    /**
     * Shows an error message.
     */
    public void showError(String message) {
        out.println(LINE);
        out.println(" OOPS!!! " + message);
        out.println(LINE);
    }

    /**
     * Shows the full task list.
     */
    public void showTaskList(List<Task> tasks) {
        out.println(LINE);
        out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        out.println(LINE);
    }

    /**
     * Shows the confirmation after marking a task as done.
     */
    public void showMarked(Task task) {
        out.println(LINE);
        out.println(" Nice! I've marked this task as done:");
        out.println("   " + task);
        out.println(LINE);
    }

    /**
     * Shows the confirmation after marking a task as not done.
     */
    public void showUnmarked(Task task) {
        out.println(LINE);
        out.println(" OK, I've marked this task as not done yet:");
        out.println("   " + task);
        out.println(LINE);
    }

    /**
     * Shows the confirmation after deleting a task.
     */
    public void showDeleted(Task task, int remainingCount) {
        out.println(LINE);
        out.println(" Noted. I've removed this task:");
        out.println("   " + task);
        out.println(" Now you have " + remainingCount + " tasks in the list.");
        out.println(LINE);
    }

    /**
     * Shows the confirmation after adding a task.
     */
    public void showAdded(Task task, int count) {
        out.println(LINE);
        out.println(" Got it. I've added this task:");
        out.println("   " + task);
        out.println(" Now you have " + count + " tasks in the list.");
        out.println(LINE);
    }

    /**
     * Shows tasks that match a search keyword.
     *
     * @param matching the list of matching tasks
     */
    public void showMatchingTasks(List<Task> matching) {
        out.println(LINE);
        out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matching.size(); i++) {
            out.println(" " + (i + 1) + "." + matching.get(i));
        }
        out.println(LINE);
    }
}
