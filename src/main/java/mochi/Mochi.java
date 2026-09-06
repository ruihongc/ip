package mochi;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import mochi.command.Command;
import mochi.place.Place;
import mochi.task.Task;
import mochi.ui.Ui;

/**
 * Mochi is a chatbot that stores tasks entered by the user, lets the user
 * mark tasks as done or not done, delete them, list them, records notes and
 * details about places, reports invalid commands, and exits on "bye".
 */
public class Mochi {
    private static final String DEFAULT_FILE_PATH = "data/tasks.txt";
    private static final String NOTES_FILE_NAME = "notes.txt";
    private static final String PLACES_FILE_NAME = "places.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final NoteList notes;
    private final PlaceList places;
    private final Ui ui;

    /**
     * Creates a Mochi chatbot with the default file path for task persistence.
     */
    public Mochi() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates a Mochi chatbot backed by the given file path for task persistence.
     * Notes and places are stored next to the task file in files named
     * "notes.txt" and "places.txt".
     *
     * @param filePath path to the file used to save and load tasks
     */
    public Mochi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage);
        tasks.add(storage.loadTasks().toArray(new Task[0]));
        Storage noteStorage = new Storage(siblingFilePath(filePath, NOTES_FILE_NAME));
        notes = new NoteList(noteStorage);
        notes.add(noteStorage.loadNotes().toArray(new String[0]));
        Storage placeStorage = new Storage(siblingFilePath(filePath, PLACES_FILE_NAME));
        places = new PlaceList(placeStorage);
        places.add(placeStorage.loadPlaces().toArray(new Place[0]));
    }

    /**
     * Resolves the path of a data file stored as a sibling of the task file.
     *
     * @param taskFilePath path of the task data file
     * @param fileName     name of the sibling data file
     * @return path of the sibling data file
     */
    private static String siblingFilePath(String taskFilePath, String fileName) {
        Path parent = Paths.get(taskFilePath).getParent();
        return parent == null ? fileName : parent.resolve(fileName).toString();
    }

    /**
     * Runs the main read-eval-print loop of the chatbot.
     */
    public void run() {
        ui.showWelcome();
        Scanner in = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            isExit = executeCommand(in.nextLine(), ui);
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
        executeCommand(fullCommand, guiUi);
        return buffer.toString().trim();
    }

    /**
     * Parses and executes a single user command against the given UI.
     *
     * @param fullCommand the raw user input
     * @param ui          the UI that receives the command output
     * @return true if the executed command asks the chatbot to exit
     */
    private boolean executeCommand(String fullCommand, Ui ui) {
        try {
            Command c = Parser.parse(fullCommand);
            c.execute(tasks, notes, places, ui);
            return c.isExit();
        } catch (MochiException e) {
            ui.showError(e.getMessage());
            return false;
        }
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
