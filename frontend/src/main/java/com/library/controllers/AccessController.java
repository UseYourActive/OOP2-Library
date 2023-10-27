package com.library.controllers;

import com.library.oop2library.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
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

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("RegisterForm.fxml"));
        Parent root = FXMLLoader.load(fxmlLoader.getLocation());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Registration Form");
        stage.setScene(scene);
        stage.show();
    }

    public void forgotPasswordLinkOnAction(ActionEvent event) {

    }
}