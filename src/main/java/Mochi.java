/**
 * Mochi is a chatbot that greets the user and waits for commands.
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
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
