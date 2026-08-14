import java.util.Scanner;

/**
 * Mochi is a chatbot that stores up to 100 tasks entered by the user,
 * lets the user mark them as done, lists them on the "list" command, and exits on "bye".
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

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
        int count = 0;
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                System.out.println(line);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + ".[" + (isDone[i] ? "X" : " ") + "] " + tasks[i]);
                }
                System.out.println(line);
            } else if (command.startsWith("mark ")) {
                int index = Integer.parseInt(command.substring(5)) - 1;
                isDone[index] = true;
                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [" + (isDone[index] ? "X" : " ") + "] " + tasks[index]);
                System.out.println(line);
            } else if (command.startsWith("unmark ")) {
                int index = Integer.parseInt(command.substring(7)) - 1;
                isDone[index] = false;
                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   [" + (isDone[index] ? "X" : " ") + "] " + tasks[index]);
                System.out.println(line);
            } else {
                tasks[count] = command;
                isDone[count] = false;
                count++;
                System.out.println(line);
                System.out.println(" added: " + command);
                System.out.println(line);
            }
            command = in.nextLine();
        }

        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
