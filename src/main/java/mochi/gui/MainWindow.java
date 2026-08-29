package mochi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mochi.Mochi;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Mochi mochi;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image mochiImage = new Image(this.getClass().getResourceAsStream("/images/DaMochi.png"));

    /**
     * Sets up the scroll pane to follow the dialog container and shows the
     * welcome message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getMochiDialog("Hello! I'm Mochi.\nWhat can I do for you?",
                mochiImage));
    }

    /**
     * Injects the Mochi instance to be used by this window.
     *
     * @param m the Mochi chatbot instance
     */
    public void setMochi(Mochi m) {
        mochi = m;
    }

    /**
     * Creates two dialog boxes, one echoing the user input and the other showing
     * Mochi's reply, and appends them to the dialog container. Clears the user
     * input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mochi.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMochiDialog(response, mochiImage)
        );
        userInput.clear();
    }
}
