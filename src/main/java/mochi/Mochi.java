package mochi;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import mochi.command.Command;
import mochi.task.Task;
import mochi.ui.Ui;

/**
 * Mochi is a chatbot that stores tasks entered by the user, lets the user
 * mark tasks as done or not done, delete them, list them, reports invalid
 * commands, and exits on "bye".
 */
public class Mochi {
    private static final String DEFAULT_FILE_PATH = "data/tasks.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a Mochi chatbot with the default file path for task persistence.
     */
    public Mochi() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates a Mochi chatbot backed by the given file path for task persistence.
     *
     * @param filePath path to the file used to save and load tasks
     */
    public Mochi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage);
        tasks.add(storage.loadTasks().toArray(new Task[0]));
    }

    /**
     * Runs the main read-eval-print loop of the chatbot.
     */
    public void run() {
        ui.showWelcome();
        Scanner in = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = in.nextLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui);
                isExit = c.isExit();
            } catch (MochiException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Processes a single user input and returns the chatbot's reply as text.
     * The reply is captured from a UI that writes to an in-memory buffer rather
     * than the console, so it can be shown in the GUI.
     *
     * @param fullCommand the raw user input
     * @return the chatbot's reply to the input
     */
    public String getResponse(String fullCommand) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Ui guiUi = new Ui(new PrintStream(buffer));
        try {
            Command c = Parser.parse(fullCommand);
            c.execute(tasks, guiUi);
        } catch (MochiException e) {
            guiUi.showError(e.getMessage());
        }
        return buffer.toString().trim();
    }

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Mochi().run();
    }
}
