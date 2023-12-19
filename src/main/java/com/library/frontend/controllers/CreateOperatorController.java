package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOperatorController implements Controller {
    @FXML public TextField usernameTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button cancelButton;
    @FXML public CheckBox showPasswordCheckBox;
    @FXML public TextField passwordTextField;
    @FXML public TextField repeatPasswordTextField;
    @FXML public PasswordField passwordPasswordField;
    @FXML public PasswordField repeatPasswordPasswordField;

    @FXML  public Label informationLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void createOperatorButtonOnMouseClicked() {
        try {
            checkAllFieldsForInput();

            User user = User.builder()
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .role(Role.OPERATOR)
                    .build();

            ((AdminService) ServiceFactory.getService(AdminService.class)).registerOperator(user);

        } catch (Exception e) {
            informationLabel.setText(e.getMessage());
        }
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml","Administrator");
    }
    @FXML
    public void showPasswordCheckBoxOnMouseClicked(MouseEvent mouseEvent) {
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

    private void checkAllFieldsForInput() throws Exception {

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
            throw new Exception("Please fill out all fields!");
        }

        if (!pass.equals(repeatPass)) {
            throw new Exception("The passwords did not match!");
        }
    }
}
