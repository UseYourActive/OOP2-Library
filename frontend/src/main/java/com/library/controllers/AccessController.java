package com.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccessController {
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label logInMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public void logInButtonOnAction(ActionEvent click){
        if(usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()){
            logInMessageLabel.setText("Please don't leave the username blank!");
        }

        if(usernameTextField.getText().isBlank()){
            logInMessageLabel.setText("Please don't leave the username blank!");
        }

        if(passwordPasswordField.getText().isBlank()){
            logInMessageLabel.setText("Please don't leave the password blank!");
        }
    }

    public void signUpButtonOnAction(ActionEvent click){

    }

    public void forgotPasswordButtonOnAction(ActionEvent click){

    }

    public void exitButtonOnAction(ActionEvent click){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}