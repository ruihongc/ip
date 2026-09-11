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

    @Test
    public void getResponse_noteLifecycle_returnsReplies() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("note buy milk").contains("added this note"));
        assertTrue(mochi.getResponse("notes").contains("buy milk"));
        assertTrue(mochi.getResponse("delete-note 1").contains("removed this note"));
    }

    @Test
    public void getResponse_placeLifecycle_returnsReplies() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("place cafes /d good wifi").contains("added this place"));
        assertTrue(mochi.getResponse("view-place 1").contains("good wifi"));
        assertTrue(mochi.getResponse("find-place wifi").contains("cafes"));
        assertTrue(mochi.getResponse("delete-place 1").contains("removed this place"));
    }

    @Test
    public void getResponse_duplicatePlace_returnsError() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        mochi.getResponse("place cafes /d good wifi");
        assertTrue(mochi.getResponse("place CAFES /d tables").contains("already have a place"));
    }

    @Test
    public void getResponse_eventEndBeforeStart_returnsError() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("event x /from 2019-01-05 /to 2019-01-01").contains("cannot be before"));
    }

    @Test
    public void getResponse_multipleSpaces_normalisesInput() {
        Mochi mochi = new Mochi(tempDir.resolve("tasks.txt").toString());
        assertTrue(mochi.getResponse("todo   read   book").contains("[T][ ] read book"));
    }
}
