package com.library.frontend.controllers.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.trying.BookRegistrationService;
import com.library.database.enums.Genre;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterNewBookController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RegisterNewBookController.class);

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

    private BookRegistrationService bookRegistrationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookRegistrationService = new BookRegistrationService();
        Platform.runLater(() -> genreComboBox.requestFocus());

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }

    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            bookRegistrationService.registerNewBook(
                    titleTextField.getText(),
                    authorTextField.getText(),
                    yearTextField.getText(),
                    resumeTextField.getText(),
                    genreComboBox.getSelectionModel().getSelectedItem(),
                    amountTextField.getText()
            );

            cancelButtonOnMouseClicked(mouseEvent);
        } catch (IncorrectInputException e) {
            informationLabel.setText(e.getMessage());
            logger.error("Error occurred during book registration", e);
        } catch (Exception e) {
            informationLabel.setText("An unexpected error occurred.");
            logger.error("Unexpected error during book registration", e);
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(
                mouseEvent,
                "/views/admin/administratorBooksScene.fxml",
                SceneLoader.getUser().getUsername() + "(Administrator)"
        );
    }
}
