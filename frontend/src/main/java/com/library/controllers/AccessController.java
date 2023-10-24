package com.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

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
    public void onLogInButtonClick(){

        logInMessageLabel.setText("");
        if(usernameTextField.getText().isBlank()&&passwordPasswordField.getText().isBlank()){
            logInMessageLabel.setText("Please enter username\nand password!");
        }
        else if(usernameTextField.getText().isBlank()){
            logInMessageLabel.setText("Please enter your username!");
        } else if(passwordPasswordField.getText().isBlank()){
            logInMessageLabel.setText("Please enter your password!");
        }


    }



    public void signUpButtonOnAction(ActionEvent click){

    }

    public void forgotPasswordLinkOnAction(ActionEvent click){

    }
}