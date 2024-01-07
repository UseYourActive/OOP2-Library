package com.library.frontend.controllers.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.BookRegistrationControllerService;
import com.library.database.enums.Genre;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

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

    private BookRegistrationControllerService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(BookRegistrationControllerService.class);

        Platform.runLater(() -> genreComboBox.requestFocus());

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }

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


    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorBooksScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }
}
