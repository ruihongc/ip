package mochi.gui;

import javafx.application.Application;

/**
 * A launcher class that serves as the entry point of the JavaFX application.
 * This classwork is needed to work around a JavaFX classpath issue where
 * launching an {@link Application} subclass directly can fail when it is the
 * main class within a shared library.
 */
public class Launcher {
    /**
     * Entry point of the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
