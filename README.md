# Mochi

> **Nya~** A friendly cat assistant that helps you keep track of your day.

Mochi is a desktop chatbot that stores the **tasks** you need to do (todos,
deadlines, and events), lets you keep free-form **notes**, and remembers
details about **places** you like. It runs as a JavaFX GUI and saves everything
automatically to your computer.

- The **User Guide** is available at [Mochi User Guide](docs/README.md).
- A text-mode interface is also included and exercised by the test suite.

## Quick start

1. Ensure you have **Java 25** installed.
2. Download the latest release of `mochi.jar`.
3. Run `java -jar mochi.jar`.
4. Type a command (e.g., `todo return book`) and press `Enter`.
5. Type `bye` to exit.

For the full list of commands, refer to the [User Guide](docs/README.md).

## Setting up the project in IntelliJ

Prerequisites: JDK 25, a recent version of IntelliJ IDEA.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close
   Project` to close the existing project first).
1. Click `Open` and select the project directory.
1. Configure the project to use **JDK 25** as explained
   [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk). In the same
   dialog, set the **Project language level** field to the `SDK default`
   option.
1. Run `src/main/java/mochi/gui/Launcher.java` to start the GUI, or
   `src/main/java/mochi/Mochi.java` for the text-mode chatbot.

**Warning:** Keep the `src/main/java` folder as the root folder for Java files
(i.e., don't rename those folders or move Java files to another folder outside
of this folder path), as this is the default location some tools (e.g.,
Gradle) expect to find Java files.

## Credit / Citation

This project started from the [SE-EDU initiative](https://se-education.org) IP
project template (originally named _Duke_) and follows the individual project
framework taught in the CS2103T software engineering course at NUS. The JavaFX
GUI structure follows the SE-EDU JavaFX tutorial for chat-based bots. The user
and assistant profile pictures (`DaUser.png`, `DaMochi.png`) are adapted from
the images provided in the CS2103T module materials. All other code is written
by the author for this project.