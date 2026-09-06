package mochi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import mochi.place.Place;
import mochi.task.Task;

/**
 * Handles loading and saving data to files on disk.
 */
public class Storage {
    private final File dataFile;

    /**
     * Creates a storage backed by the given file path.
     */
    public Storage(String filePath) {
        this.dataFile = Paths.get(filePath).toFile();
    }

    /**
     * Loads tasks from the data file, creating the directory and file if needed.
     */
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        for (String line : loadLines()) {
            try {
                Task task = Task.fromFileString(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (MochiException e) {
                // Skip corrupted lines silently.
            }
        }
        return tasks;
    }

    /**
     * Loads notes from the data file, creating the directory and file if needed.
     */
    public List<String> loadNotes() {
        return loadLines();
    }

    /**
     * Loads places from the data file, creating the directory and file if needed.
     */
    public List<Place> loadPlaces() {
        List<Place> places = new ArrayList<>();
        for (String line : loadLines()) {
            try {
                places.add(Place.fromFileString(line));
            } catch (MochiException e) {
                // Skip corrupted lines silently.
            }
        }
        return places;
    }

    /**
     * Saves the given list of tasks to the data file.
     */
    public void saveTasks(List<Task> tasks) {
        writeLines(tasks.stream()
                .map(Task::toFileString)
                .collect(Collectors.toList()));
    }

    /**
     * Saves the given list of notes to the data file.
     */
    public void saveNotes(List<String> notes) {
        writeLines(notes);
    }

    /**
     * Saves the given list of places to the data file.
     */
    public void savePlaces(List<Place> places) {
        writeLines(places.stream()
                .map(Place::toFileString)
                .collect(Collectors.toList()));
    }

    /**
     * Reads the non-empty lines of the data file, creating the directory and
     * file if needed.
     *
     * @return the file lines in order
     */
    private List<String> loadLines() {
        List<String> lines = new ArrayList<>();

        ensureDataFileExists();

        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist yet; return an empty list.
        }
        return lines;
    }

    /**
     * Writes the given lines, one per line, to the data file.
     *
     * @param lines the lines to write in order
     */
    private void writeLines(List<String> lines) {
        ensureDataFileExists();
        assert dataFile.getParentFile() == null || dataFile.getParentFile().exists()
                : "The data directory must exist before saving";

        String content = String.join(System.lineSeparator(), lines);

        try (FileWriter writer = new FileWriter(dataFile)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Warning: could not save data to file.");
        }
    }

    /**
     * Creates the parent directory and the data file if they do not exist yet.
     */
    private void ensureDataFileExists() {
        File dir = dataFile.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                // Leave the file missing; callers handle it gracefully.
            }
        }
    }
}
