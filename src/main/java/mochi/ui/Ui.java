package mochi.ui;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import mochi.place.Place;
import mochi.task.Task;

/**
 * Handles all user interface operations: displaying messages and reading input.
 * Mochi speaks with a playful cat personality, using nya~ and cat expressions.
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
        out.println("Nya~ Hello! I'm Mochi!");
        out.println("I'll help you keep your life organised~");
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
        out.println("Purr~ See you next time!");
        out.println(LINE);
    }

    /**
     * Shows an error message.
     */
    public void showError(String message) {
        out.println(LINE);
        out.println(" Nya-no!!! " + message);
        out.println(LINE);
    }

    /**
     * Shows the full task list.
     */
    public void showTaskList(List<Task> tasks) {
        showNumberedList(" Here are the tasks in your list nya~", tasks);
    }

    /**
     * Shows the full note list.
     */
    public void showNoteList(List<String> notes) {
        showNumberedList(" Here are your notes nya~", notes);
    }

    /**
     * Shows the full place list.
     */
    public void showPlaceList(List<Place> places) {
        showNumberedList(" Here are the places in your list nya~", places);
    }

    /**
     * Shows the confirmation after marking a task as done.
     */
    public void showMarked(Task task) {
        out.println(LINE);
        out.println(" Purr~ I've marked this task as done:");
        out.println("   " + task);
        out.println(LINE);
    }

    /**
     * Shows the confirmation after marking a task as not done.
     */
    public void showUnmarked(Task task) {
        out.println(LINE);
        out.println(" OK~ I've unmarked this task:");
        out.println("   " + task);
        out.println(LINE);
    }

    /**
     * Shows the confirmation after deleting a task.
     */
    public void showDeleted(Task task, int remainingCount) {
        out.println(LINE);
        out.println(" Nya~ I've removed this task:");
        out.println("   " + task);
        out.println(" You have " + remainingCount + " task" + (remainingCount == 1 ? "" : "s") + " left nya~");
        out.println(LINE);
    }

    /**
     * Shows the confirmation after adding a task.
     */
    public void showAdded(Task task, int count) {
        out.println(LINE);
        out.println(" Got it! I've added this task nya~");
        out.println("   " + task);
        out.println(" You now have " + count + " task" + (count == 1 ? "" : "s") + " in the list~");
        out.println(LINE);
    }

    /**
     * Shows the confirmation after adding a note.
     */
    public void showAddedNote(String note, int count) {
        out.println(LINE);
        out.println(" Got it! I've added this note nya~");
        out.println("   " + note);
        out.println(" You now have " + count + " note" + (count == 1 ? "" : "s") + " in the list~");
        out.println(LINE);
    }

    /**
     * Shows the confirmation after deleting a note.
     */
    public void showDeletedNote(String note, int remainingCount) {
        out.println(LINE);
        out.println(" Nya~ I've removed this note:");
        out.println("   " + note);
        out.println(" You have " + remainingCount + " note" + (remainingCount == 1 ? "" : "s") + " left nya~");
        out.println(LINE);
    }

    /**
     * Shows notes that match a search keyword.
     *
     * @param matching the list of matching notes
     */
    public void showMatchingNotes(List<String> matching) {
        showNumberedList(" Here are the matching notes nya~", matching);
    }

    /**
     * Shows the confirmation after adding a place.
     */
    public void showAddedPlace(Place place, int count) {
        out.println(LINE);
        out.println(" Got it! I've added this place nya~");
        out.println("   " + place);
        out.println(" You now have " + count + " place" + (count == 1 ? "" : "s") + " in the list~");
        out.println(LINE);
    }

    /**
     * Shows the confirmation after deleting a place.
     */
    public void showDeletedPlace(Place place, int remainingCount) {
        out.println(LINE);
        out.println(" Nya~ I've removed this place:");
        out.println("   " + place);
        out.println(" You have " + remainingCount + " place" + (remainingCount == 1 ? "" : "s") + " left nya~");
        out.println(LINE);
    }

    /**
     * Shows the details recorded about a place.
     *
     * @param place the place whose details are to be shown
     */
    public void showPlaceDetails(Place place) {
        out.println(LINE);
        out.println(" Here are the details of " + place.getName() + " nya~");
        IntStream.range(0, place.getDetails().size())
                .forEach(i -> out.println(" " + (i + 1) + "." + place.getDetails().get(i)));
        out.println(LINE);
    }

    /**
     * Shows places that match a search keyword.
     *
     * @param matching the list of matching places
     */
    public void showMatchingPlaces(List<Place> matching) {
        showNumberedList(" Here are the matching places nya~", matching);
    }

    /**
     * Shows tasks that match a search keyword.
     *
     * @param matching the list of matching tasks
     */
    public void showMatchingTasks(List<Task> matching) {
        showNumberedList(" Here are the matching tasks nya~", matching);
    }

    /**
     * Shows a numbered list of items under a heading, delimited by divider lines.
     *
     * @param heading the heading line to print above the list
     * @param items   the items to display, in display order
     */
    private void showNumberedList(String heading, List<?> items) {
        out.println(LINE);
        out.println(heading);
        IntStream.range(0, items.size())
                .forEach(i -> out.println(" " + (i + 1) + "." + items.get(i)));
        out.println(LINE);
    }
}
