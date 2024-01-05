package com.library.frontend.controllers.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.trying.OperatorCreationService;
import com.library.backend.services.trying.PasswordFieldsService;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOperatorController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreateOperatorController.class);

    @FXML public TextField usernameTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button cancelButton;
    @FXML public CheckBox showPasswordCheckBox;
    @FXML public PasswordField passwordPasswordField;
    @FXML public PasswordField repeatPasswordPasswordField;
    @FXML public TextField passwordTextField;
    @FXML public TextField repeatPasswordTextField;
    @FXML public Label informationLabel;

    private OperatorCreationService operatorCreationService;
    private PasswordFieldsService passwordFieldsService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorCreationService = new OperatorCreationService();
        passwordFieldsService = new PasswordFieldsService();
    }

    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            String username = usernameTextField.getText();
            String password = passwordFieldsService.getPasswordFieldText(passwordPasswordField, passwordTextField);
            String repeatPassword = passwordFieldsService.getPasswordFieldText(repeatPasswordPasswordField, repeatPasswordTextField);

            operatorCreationService.createOperator(username, password, repeatPassword);

            SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
            logger.info("Operator creation successful for username: '{}'", username);
        } catch (IncorrectInputException e) {
            informationLabel.setText(e.getMessage());
            logger.error("Error creating operator", e);
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    @FXML
    public void showPasswordCheckBoxOnMouseClicked() {
        boolean showPassword = showPasswordCheckBox.isSelected();
        passwordFieldsService.updatePasswordFieldsVisibility(showPassword, passwordPasswordField, passwordTextField);
        passwordFieldsService.updatePasswordFieldsVisibility(showPassword, repeatPasswordPasswordField, repeatPasswordTextField);
    }
}
