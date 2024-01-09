package com.library.frontend.controllers.operator;

import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for displaying the resume content in a separate window.
 * This controller is responsible for initializing and handling the UI elements of the resume display window.
 */
public class ResumeShowController implements Controller {

    /** Button to close the resume display window. */
    @FXML public Button closeButton;

    /** TextArea for displaying the content of the resume. */
    @FXML public TextArea textArea;

    /**
     * Initializes the ResumeShowController.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the resume content from the transferable objects passed during scene loading
        String resume = ((String) SceneLoader.getTransferableObjects()[0]);

        // Set the resume content to the TextArea for display
        textArea.setText(resume);
    }

    /**
     * Handles the mouse click event on the close button, closing the resume display window.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // Close the window when the close button is clicked
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}

