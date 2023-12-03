package com.project.frontend.controllers;

import com.project.backend.operations.OperationFactory;
import com.project.backend.operations.processors.CreateUserOperationProcessor;
import com.project.backend.operations.requests.CreateUserRequest;
import com.project.backend.operations.responses.CreateUserResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class RegistrationController implements Controller {
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
    private Label registrationMessageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> firstNameTextField.requestFocus());
    }

    @FXML
    public void onRegisterButtonClick() {

        try {
            checkAllFieldsForInput();

            CreateUserRequest request = CreateUserRequest.builder()
                    .firstName(firstNameTextField.getText())
                    .middleName(middleNameTextField.getText())
                    .lastName(lastNameTextField.getText())
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .email(emailTextField.getText())
                    .build();

            CreateUserOperationProcessor processor = OperationFactory.getOperationProcessor(CreateUserOperationProcessor.class);
            CreateUserResponse createUserResponse = processor.process(request);


        } catch (Exception e) {
            registrationMessageLabel.setText(e.getMessage());
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

    private void checkAllFieldsForInput() throws Exception {


        String firstNameTextFieldText = firstNameTextField.getText();
        String middleNameTextFieldText = middleNameTextField.getText();
        String lastNameTextFieldText = lastNameTextField.getText();
        String usernameTextFieldText = usernameTextField.getText();
        String email = emailTextField.getText();
        String pass;
        String repeatPass;

        if (passwordPasswordField.isVisible()) {
            pass = passwordPasswordField.getText();
            repeatPass = repeatPasswordPasswordField.getText();
        } else {
            pass = visiblePasswordTextField.getText();
            repeatPass = visibleRepeatPasswordTextField.getText();
        }

        if (firstNameTextFieldText.isEmpty() ||
                middleNameTextFieldText.isEmpty() ||
                lastNameTextFieldText.isEmpty() ||
                usernameTextFieldText.isEmpty() ||
                email.isEmpty() ||
                pass.isEmpty() ||
                repeatPass.isEmpty()) {
            throw new Exception("Please fill out all fields!");
        }

        if (!pass.equals(repeatPass)) {
            throw new Exception("The passwords did not match!");
        }
    }

}