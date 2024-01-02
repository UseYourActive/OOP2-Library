package com.library.frontend.controllers.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.AdminService;
import com.library.backend.services.Service;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.validators.StrongPasswordValidator;
import com.library.frontend.utils.validators.Validator;
import jakarta.validation.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

public class CreateOperatorController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreateOperatorController.class);

    @FXML public TextField usernameTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button cancelButton;
    @FXML public CheckBox showPasswordCheckBox;
    @FXML public TextField passwordTextField;
    @FXML public TextField repeatPasswordTextField;
    @FXML public PasswordField passwordPasswordField;
    @FXML public PasswordField repeatPasswordPasswordField;
    @FXML public Label informationLabel;
    private Validator passwordValidator;
    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService=ServiceFactory.getService(AdminService.class);
        passwordValidator = new StrongPasswordValidator();
    }

    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            checkAllFieldsForInput();

            User user = User.builder()
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .role(Role.OPERATOR)
                    .build();

            adminService.registerOperator(user);
            cancelButton.fire();
        } catch (IncorrectInputException | PatternSyntaxException | ValidationException e) {
            informationLabel.setText(e.getMessage());
            logger.error("User failed to create due to missing fields", e);
        }

        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void showPasswordCheckBoxOnMouseClicked() {
        togglePasswordVisibility();
    }

    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            passwordTextField.setText(passwordPasswordField.getText());
            repeatPasswordTextField.setText(repeatPasswordPasswordField.getText());
            passwordTextField.setVisible(true);
            passwordPasswordField.setVisible(false);

            repeatPasswordTextField.setVisible(true);
            repeatPasswordPasswordField.setVisible(false);
        } else {
            passwordPasswordField.setText(passwordTextField.getText());
            repeatPasswordPasswordField.setText(repeatPasswordTextField.getText());
            passwordTextField.setVisible(false);
            passwordPasswordField.setVisible(true);

            repeatPasswordTextField.setVisible(false);
            repeatPasswordPasswordField.setVisible(true);
        }
    }

    private void checkAllFieldsForInput() throws IncorrectInputException {
        String usernameTextFieldText = usernameTextField.getText();
        String pass;
        String repeatPass;

        if (passwordPasswordField.isVisible()) {
            pass = passwordPasswordField.getText();
            repeatPass = repeatPasswordPasswordField.getText();
        } else {
            pass = passwordTextField.getText();
            repeatPass = repeatPasswordTextField.getText();
        }

        if (usernameTextFieldText.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
            throw new IncorrectInputException("Please fill out all fields!");
        }

        if (!pass.equals(repeatPass)) {
            throw new IncorrectInputException("The passwords did not match!");
        }

        if (!passwordValidator.isValid(pass)) {
            throw new IncorrectInputException("Password is not strong enough. It must contain at least one digit, one lowercase and one uppercase letter, and be at least 6 characters long.");
        }
    }
}
