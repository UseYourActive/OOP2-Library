package com.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationController {
    @FXML
    private Button registerButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField repeatPasswordPasswordField;
    @FXML
    private PasswordField resultLabel;

    @FXML
    public void onRegisterButtonClick() {
        if(!passwordPasswordField.getText().equals(repeatPasswordPasswordField.getText())){
            resultLabel.setText("The passwords did not match!");
        }

        if (firstNameTextField.getText().isEmpty() ||
                middleNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty() ||
                passwordPasswordField.getText().isEmpty() ||
                repeatPasswordPasswordField.getText().isEmpty()) {
            resultLabel.setText("Please fill out all fields!");
        }
    }
}
