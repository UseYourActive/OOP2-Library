package com.library.controllers;

import com.library.oop2library.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

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
    private RegistrationController registrationController;

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

    public void signUpButtonOnAction() throws IOException {

    }

    public void forgotPasswordLinkOnAction(ActionEvent click) {

    }
}