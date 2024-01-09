package com.library.frontend.controllers.admin;

import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.CreateOperatorService;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for creating new operator accounts in the administrator view.
 */
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

    private CreateOperatorService service;

    /**
     * Initializes the controller with the necessary service for creating operator accounts.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(CreateOperatorService.class);
    }

    /**
     * Handles the mouse click event on the "Create Operator" button, attempting to create a new operator account.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
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

    /**
     * Handles the mouse click event on the "Cancel" button, navigating back to the administrator operators scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    /**
     * Handles the mouse click event on the "Show Password" checkbox, toggling the visibility of password fields.
     */
    @FXML
    public void showPasswordCheckBoxOnMouseClicked() {
        boolean showPassword = showPasswordCheckBox.isSelected();
        updatePasswordFieldsVisibility(showPassword, passwordPasswordField, passwordTextField);
        updatePasswordFieldsVisibility(showPassword, repeatPasswordPasswordField, repeatPasswordTextField);
    }

    /**
     * Updates the visibility of password fields based on the "Show Password" checkbox.
     *
     * @param showPassword  A boolean indicating whether to show the password.
     * @param hiddenField   The PasswordField used for hidden password input.
     * @param visibleField  The TextField used for visible password input.
     */
    private void updatePasswordFieldsVisibility(boolean showPassword, PasswordField hiddenField, TextField visibleField) {
        hiddenField.setVisible(!showPassword);
        visibleField.setVisible(showPassword);

        if (showPassword) {
            visibleField.setText(hiddenField.getText());
        } else {
            hiddenField.setText(visibleField.getText());
        }
    }

    /**
     * Gets the text from either the hidden PasswordField or the visible TextField based on visibility.
     *
     * @param hiddenField  The PasswordField used for hidden password input.
     * @param visibleField The TextField used for visible password input.
     * @return A String representing the password text.
     */
    private String getPasswordFieldText(PasswordField hiddenField, TextField visibleField) {
        return hiddenField.isVisible() ? hiddenField.getText() : visibleField.getText();
    }
}
