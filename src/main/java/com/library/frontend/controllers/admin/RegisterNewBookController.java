package com.library.frontend.controllers.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.BookRegistrationService;
import com.library.database.enums.Genre;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for registering new books in the administrator view.
 */
public class RegisterNewBookController implements Controller {

    @FXML public TextField titleTextField;
    @FXML public TextField authorTextField;
    @FXML public TextField yearTextField;
    @FXML public TextArea resumeTextField;
    @FXML public ComboBox<Genre> genreComboBox;
    @FXML public Button registerButton;
    @FXML public Button cancelButton;
    @FXML public Label informationLabel;
    @FXML public TextField amountTextField;
    @FXML public AnchorPane anchorPane;

    private BookRegistrationService service;

    /**
     * Initializes the controller with the necessary service for book registration and sets up the genre ComboBox.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(BookRegistrationService.class);

        Platform.runLater(() -> genreComboBox.requestFocus());

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }

    /**
     * Handles the mouse click event on the "Register" button, attempting to register a new book.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            service.registerNewBook(
                    titleTextField.getText(),
                    authorTextField.getText(),
                    yearTextField.getText(),
                    resumeTextField.getText(),
                    genreComboBox.getSelectionModel().getSelectedItem(),
                    amountTextField.getText()
            );

            cancelButtonOnMouseClicked(mouseEvent);
        } catch (IncorrectInputException | NumberFormatException e) {
            informationLabel.setText(e.getMessage());
        }
    }

    /**
     * Handles the mouse click event on the "Cancel" button, navigating back to the administrator books scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorBooksScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }
}
