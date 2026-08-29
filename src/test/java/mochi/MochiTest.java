package mochi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for the Mochi chatbot's response generation used by the GUI.
 */
public class MochiTest {

    @TempDir
    public Path tempDir;

    @Test
    public void getResponse_greetingAndTask_returnsReplies() throws MochiException {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("todo read book").contains("Got it. I've added this task"));
        assertTrue(mochi.getResponse("list").contains("1.[T][ ] read book"));
    }

    @Test
    public void getResponse_unknownCommand_returnsError() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("blah").contains("I don't know what that means"));
    }
}
