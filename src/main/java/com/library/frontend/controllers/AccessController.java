package com.library.frontend.controllers;


import com.library.LibraryApplication;
import com.library.backend.operations.OperationProcessorFactory;
import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class AccessController{
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

    @Setter
    private LogInOperation logInOperation;

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

        LogInRequest request=LogInRequest.builder()
                .username(usernameTextField.getText())
                .password(passwordPasswordField.getText())
                .build();

        try {
            LogInResponse response = logInOperation.process(request);
            switch (response.getRole()){
                case "ADMIN" -> {}
                case "OPERATOR" ->{}
                case "CLIENT" ->{}
            }

        }catch (Exception e){
            //
        }
    }

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        OperationProcessorFactory operationProcessorFactory=new OperationProcessorFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("/com.library/views/RegisterForm.fxml"));
        Parent root = fxmlLoader.load();

        RegistrationController controller=fxmlLoader.getController();
        controller.setCreateUserOperation(operationProcessorFactory.getUserOperation());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Registration Form");
        stage.setScene(scene);
        stage.show();
    }

    public void forgotPasswordLinkOnAction(ActionEvent event) {

    }
}