package mochi.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an {@code ImageView} to represent the
 * speaker's face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final double USER_IMAGE_SIZE = 60;
    private static final double MOCHI_IMAGE_SIZE = 100;
    private static final String ERROR_PREFIX = "Nya-no!!!";

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box showing the given text and speaker image.
     *
     * @param text the text spoken by the speaker
     * @param img  the image representing the speaker
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box so that the image is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Returns a dialog box for user input, with the image on the right.
     * The user's bubble is styled in blue and uses a smaller font and picture
     * than Mochi's bubble, giving the two speakers differently sized bubbles.
     *
     * @param text the user's input text
     * @param img  the image representing the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("dialog-user");
        db.dialog.getStyleClass().add("bubble-user");
        db.displayPicture.setFitWidth(USER_IMAGE_SIZE);
        db.displayPicture.setFitHeight(USER_IMAGE_SIZE);
        return db;
    }

    /**
     * Returns a dialog box for Mochi's reply, with the image on the left.
     * Error replies are highlighted in red with their own bubble style.
     *
     * @param text the reply text
     * @param img  the image representing Mochi
     */
    public static DialogBox getMochiDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        db.getStyleClass().add("dialog-mochi");
        if (text.contains(ERROR_PREFIX)) {
            db.dialog.getStyleClass().add("bubble-error");
        } else {
            db.dialog.getStyleClass().add("bubble-mochi");
        }
        db.displayPicture.setFitWidth(MOCHI_IMAGE_SIZE);
        db.displayPicture.setFitHeight(MOCHI_IMAGE_SIZE);
        return db;
    }
}
