import java.util.Scanner;

/**
 * Mochi is a chatbot that stores up to 100 tasks entered by the user,
 * lets the user mark tasks as done or not done, lists them on the "list"
 * command, and exits on "bye".
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

        Task[] tasks = new Task[100];
        int count = 0;
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                System.out.println(line);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println(line);
            } else if (command.startsWith("mark ")) {
                int index = Integer.parseInt(command.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index]);
                System.out.println(line);
            } else if (command.startsWith("unmark ")) {
                int index = Integer.parseInt(command.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index]);
                System.out.println(line);
            } else if (command.startsWith("todo ")) {
                tasks[count] = new Task(command.substring(5));
                count++;
                printAdded(line, tasks[count - 1], count);
            } else if (command.startsWith("deadline ")) {
                String[] parts = command.substring(9).split(" /by ", 2);
                tasks[count] = new Task('D', parts[0], parts[1], null, null);
                count++;
                printAdded(line, tasks[count - 1], count);
            } else if (command.startsWith("event ")) {
                String[] fromParts = command.substring(6).split(" /from ", 2);
                String[] toParts = fromParts[1].split(" /to ", 2);
                tasks[count] = new Task('E', fromParts[0], null, toParts[0], toParts[1]);
                count++;
                printAdded(line, tasks[count - 1], count);
            }
            command = in.nextLine();
        }

        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
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
}
