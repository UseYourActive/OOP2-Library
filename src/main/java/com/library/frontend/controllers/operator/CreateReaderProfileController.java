package com.library.frontend.controllers.operator;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.CreateReaderProfileControllerService;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateReaderProfileController implements Controller {

    @FXML public TextField firstNameTextField;
    @FXML public TextField middleNameTextField;
    @FXML public TextField lastNameTextField;
    @FXML public TextField phoneNumberTextField;
    @FXML public TextField emailTextField;
    @FXML public Button createReaderProfileButton;
    @FXML public Button cancelButton;
    @FXML public Label infoLabel;

    private CreateReaderProfileControllerService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(CreateReaderProfileControllerService.class);
    }

    @FXML
    public void createReaderProfileButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            String firstName = firstNameTextField.getText();
            String middleName = middleNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();

            service.createReader(firstName, middleName, lastName, phoneNumber, email);

            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", SceneLoader.getUser().getUsername() + "(Operator)");
        } catch (IncorrectInputException e) {
            infoLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Operator readers scene");
        }
    }
}
