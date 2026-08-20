import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading tasks from and saving tasks to a file on disk.
 * The file uses a pipe-delimited format, one task per line:
 * TYPE | done (0 or 1) | description | extra fields...
 */
public class Storage {
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "tasks.txt";

    /**
     * Returns the full path to the data file as a {@code File} object.
     */
    private static File getDataFile() {
        return Paths.get(DATA_DIR, FILE_NAME).toFile();
    }

    /**
     * Loads tasks from the data file.
     * Creates the directory and an empty file if either does not exist yet.
     * Silently skips lines that cannot be parsed (corrupted data).
     *
     * @return a list of tasks read from the file
     */
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = getDataFile();

        // Create the data directory if it does not exist.
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create an empty file if one does not exist yet.
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // If we cannot create the file, start with an empty list.
                return tasks;
            }
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = Task.fromFileString(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (MochiException e) {
                    // Skip corrupted lines silently.
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist yet; return an empty list.
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the data file, overwriting any existing content.
     * Creates the data directory if it does not exist.
     *
     * @param tasks the list of tasks to save
     */
    public void saveTasks(List<Task> tasks) {
        File file = getDataFile();

        // Create the data directory if it does not exist.
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Warning: could not save tasks to file.");
        }
    }
}
