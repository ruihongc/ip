package mochi;

import java.util.Scanner;

import mochi.command.Command;
import mochi.ui.Ui;

/**
 * Mochi is a chatbot that stores tasks entered by the user, lets the user
 * mark tasks as done or not done, delete them, list them, reports invalid
 * commands, and exits on "bye".
 */
public class Mochi {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Mochi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage);
        storage.loadTasks().forEach(tasks.getTasks()::add);
    }

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

    public static void main(String[] args) {
        new Mochi("data/tasks.txt").run();
    }
}
