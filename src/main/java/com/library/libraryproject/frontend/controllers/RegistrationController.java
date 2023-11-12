package com.library.libraryproject.frontend.controllers;

import com.library.libraryproject.backend.operations.CreateUserOperation;
import com.library.libraryproject.backend.requests.CreateUserRequest;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@NoArgsConstructor
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
//    @FXML
//    private TextField phoneNumberField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField repeatPasswordPasswordField;
    @FXML
    private PasswordField messageRegistrationLabel;

    private CreateUserOperation createUserOperation;

    @Autowired
    public RegistrationController(CreateUserOperation createUserOperation) {
        this.createUserOperation = createUserOperation;
    }

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

        String firstName = firstNameTextField.getText();
        String middleName = middleNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String email = emailTextField.getText();

        CreateUserRequest build = CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .middleName(middleName)
                .password(password)
                .email(email)
                .build();

        createUserOperation.process(build);
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
