package com.library.libraryproject.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {
    @FXML
    private Button registerButton;

    @FXML
    private TextField visiblePasswordTextField;

    @FXML
    private TextField visibleRepeatPasswordTextField;
    @FXML
    private CheckBox showPasswordCheckBox;
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
    private PasswordField messageRegistrationLabel;

    @FXML
    public void onRegisterButtonClick() {
        if (!passwordPasswordField.getText().equals(repeatPasswordPasswordField.getText())) {
            messageRegistrationLabel.setText("The passwords did not match!");
        }

        if (firstNameTextField.getText().isEmpty() ||
                middleNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty() ||
                passwordPasswordField.getText().isEmpty() ||
                repeatPasswordPasswordField.getText().isEmpty()) {
            messageRegistrationLabel.setText("Please fill out all fields!");
        }
    }

    @FXML
    public void onCheckUncheck() {
        if (showPasswordCheckBox.isSelected()) {
            visiblePasswordTextField.setText(passwordPasswordField.getText());
            visibleRepeatPasswordTextField.setText(repeatPasswordPasswordField.getText());
            visiblePasswordTextField.setVisible(true);
            passwordPasswordField.setVisible(false);

            visibleRepeatPasswordTextField.setVisible(true);
            repeatPasswordPasswordField.setVisible(false);
        } else {
            passwordPasswordField.setText(visiblePasswordTextField.getText());
            repeatPasswordPasswordField.setText(visibleRepeatPasswordTextField.getText());
            visiblePasswordTextField.setVisible(false);
            passwordPasswordField.setVisible(true);

            visibleRepeatPasswordTextField.setVisible(false);
            repeatPasswordPasswordField.setVisible(true);
        }
    }
}
