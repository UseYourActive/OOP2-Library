package com.library.frontend.controllers.admin;

import com.library.backend.services.admin.OperatorControllerService;
import com.library.database.repositories.UserRepository;
import com.library.frontend.controllers.Controller;
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
    @FXML public PasswordField passwordPasswordField;
    @FXML public PasswordField repeatPasswordPasswordField;
    @FXML public TextField passwordTextField;
    @FXML public TextField repeatPasswordTextField;
    @FXML public Label informationLabel;

    private OperatorControllerService service;
    //private AdminService adminService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //adminService= ServiceFactory.getService(AdminService.class);
        service=new OperatorControllerService(UserRepository.getInstance());
    }

    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            String username = usernameTextField.getText();
            String password = getPasswordFieldText(passwordPasswordField, passwordTextField);
            String repeatPassword = getPasswordFieldText(repeatPasswordPasswordField, repeatPasswordTextField);

            service.createOperator(username, password, repeatPassword);

            SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
        } catch (Exception e) {
            informationLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    @FXML
    public void showPasswordCheckBoxOnMouseClicked() {
        boolean showPassword = showPasswordCheckBox.isSelected();
        updatePasswordFieldsVisibility(showPassword, passwordPasswordField, passwordTextField);
        updatePasswordFieldsVisibility(showPassword, repeatPasswordPasswordField, repeatPasswordTextField);
    }

    private void updatePasswordFieldsVisibility(boolean showPassword, PasswordField hiddenField, TextField visibleField) {
        hiddenField.setVisible(!showPassword);
        visibleField.setVisible(showPassword);

        if (showPassword) {
            visibleField.setText(hiddenField.getText());
        } else {
            hiddenField.setText(visibleField.getText());
        }
    }

    private String getPasswordFieldText(PasswordField hiddenField, TextField visibleField) {
        return hiddenField.isVisible() ? hiddenField.getText() : visibleField.getText();
    }
}
