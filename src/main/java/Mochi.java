import java.util.List;
import java.util.Scanner;

/**
 * Mochi is a chatbot that stores tasks entered by the user, lets the user
 * mark tasks as done or not done, delete them, list them on the "list"
 * command, reports invalid commands, and exits on "bye".
 */
public class Mochi {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = "    __  ___           __    _\n"
                + "   /  |/  /___  _____/ /_  (_)\n"
                + "  / /|_/ / __ \\/ ___/ __ \\/ /\n"
                + " / /  / / /_/ / /__/ / / / /\n"
                + "/_/  /_/\\____/\\___/_/ /_/_/";
        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Storage storage = new Storage();
        TaskList taskList = new TaskList(storage);
        storage.loadTasks().forEach(taskList.getTasks()::add);

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (!command.equals("bye")) {
            try {
                handleCommand(line, command, taskList);
            } catch (MochiException e) {
                printError(line, e.getMessage());
            }
            command = in.nextLine();
        }

        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    /**
     * Handles a single command, adding, modifying, or removing tasks from the
     * list as needed. Throws a MochiException if the command is invalid.
     */
    private static void handleCommand(String line, String command, TaskList taskList) throws MochiException {
        int spaceIndex = command.indexOf(' ');
        String verb = spaceIndex == -1 ? command : command.substring(0, spaceIndex);
        String args = spaceIndex == -1 ? "" : command.substring(spaceIndex + 1);
        List<Task> tasks = taskList.getTasks();

        if (verb.equals("list")) {
            System.out.println(line);
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            System.out.println(line);
        } else if (verb.equals("mark")) {
            int index = parseTaskNumber(args) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MochiException("There is no task number " + (index + 1) + " in the list.");
            }
            taskList.mark(index);
            System.out.println(line);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(index));
            System.out.println(line);
        } else if (verb.equals("unmark")) {
            int index = parseTaskNumber(args) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MochiException("There is no task number " + (index + 1) + " in the list.");
            }
            taskList.unmark(index);
            System.out.println(line);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(index));
            System.out.println(line);
        } else if (verb.equals("delete")) {
            int index = parseTaskNumber(args) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MochiException("There is no task number " + (index + 1) + " in the list.");
            }
            Task removed = taskList.remove(index);
            System.out.println(line);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(line);
        } else if (verb.equals("todo")) {
            if (args.isEmpty()) {
                throw new MochiException("The description of a todo cannot be empty.");
            }
            taskList.add(new Todo(args));
            printAdded(line, tasks.get(tasks.size() - 1), tasks.size());
        } else if (verb.equals("deadline")) {
            String[] parts = args.split(" /by ", 2);
            if (parts[0].isEmpty()) {
                throw new MochiException("The description of a deadline cannot be empty.");
            }
            if (parts.length < 2) {
                throw new MochiException("Please add the deadline with /by, e.g., deadline return book /by Sunday");
            }
            taskList.add(new Deadline(parts[0], parts[1]));
            printAdded(line, tasks.get(tasks.size() - 1), tasks.size());
        } else if (verb.equals("event")) {
            String[] fromParts = args.split(" /from ", 2);
            if (fromParts[0].isEmpty()) {
                throw new MochiException("The description of an event cannot be empty.");
            }
            if (fromParts.length < 2) {
                throw new MochiException("Please add the start time with /from, e.g., event project meeting /from Mon 2pm /to 4pm");
            }
            String[] toParts = fromParts[1].split(" /to ", 2);
            if (toParts.length < 2) {
                throw new MochiException("Please add the end time with /to, e.g., event project meeting /from Mon 2pm /to 4pm");
            }
            taskList.add(new Event(fromParts[0], toParts[0], toParts[1]));
            printAdded(line, tasks.get(tasks.size() - 1), tasks.size());
        } else {
            throw new MochiException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses a 1-based task number from the given text.
     */
    private static int parseTaskNumber(String input) throws MochiException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new MochiException("Please give a task number, e.g., mark 2");
        }
    }

    /**
     * Prints the confirmation message after adding a task.
     */
    private static void printAdded(String line, Task task, int count) {
        System.out.println(line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + count + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Prints the error message for an invalid command.
     */
    private static void printError(String line, String message) {
        System.out.println(line);
        System.out.println(" OOPS!!! " + message);
        System.out.println(line);
    }
}
