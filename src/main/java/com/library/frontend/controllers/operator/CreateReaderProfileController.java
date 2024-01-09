package com.library.frontend.controllers.operator;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.CreateReaderProfileService;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for creating a new reader profile in the operator view.
 */
public class CreateReaderProfileController implements Controller {

    @FXML public TextField firstNameTextField;
    @FXML public TextField middleNameTextField;
    @FXML public TextField lastNameTextField;
    @FXML public TextField phoneNumberTextField;
    @FXML public TextField emailTextField;
    @FXML public Button createReaderProfileButton;
    @FXML public Button cancelButton;
    @FXML public Label infoLabel;

    private CreateReaderProfileService service;

    /**
     * Initializes the controller and loads the necessary services.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(CreateReaderProfileService.class);
    }

    /**
     * Handles the mouse click event on the "Create Reader Profile" button, creating a new reader profile.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void createReaderProfileButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            String firstName = firstNameTextField.getText();
            String middleName = middleNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();

            // Create a new reader profile using the provided information
            service.createReader(firstName, middleName, lastName, phoneNumber, email);

            // Load the operator readers scene after creating the reader profile
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", SceneLoader.getUser().getUsername() + "(Operator)");
        } catch (IncorrectInputException e) {
            // Display error message if input validation fails
            infoLabel.setText(e.getMessage());
        }
    }

    /**
     * Handles the mouse click event on the "Cancel" button, returning to the operator readers scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // Load the operator readers scene on cancel
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Operator readers scene");
        }
    }
}
