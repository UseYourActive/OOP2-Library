package com.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AccessController {
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Hyperlink forgotPasswordHyperlink;
    @FXML
    private Label logInMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    public void onLogInButtonClick() {
        if (usernameTextField.getText().isBlank()) {
            logInMessageLabel.setText("Please enter your username!");
        }

        if (passwordPasswordField.getText().isBlank()) {
            logInMessageLabel.setText("Please enter your password!");
        }

        if (usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()) {
            logInMessageLabel.setText("Please enter username\nand password!");
        }
    }

    public void signUpButtonOnAction(ActionEvent click) {

    }

    public void forgotPasswordLinkOnAction(ActionEvent click) {

    }
}