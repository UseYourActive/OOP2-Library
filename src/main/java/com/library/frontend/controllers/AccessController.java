package com.library.frontend.controllers;

import com.library.backend.services.AccessService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.Form;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class AccessController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> usernameTextField.requestFocus());
    }

    @FXML
    public void logInButtonOnAction(ActionEvent event) {
        try {
            checkInput();

            User logInUser = User.builder()
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .build();

            AccessService service = (AccessService) ServiceFactory.getService(AccessService.class);

            User user = service.getUser(logInUser);

            Form form = null;
            switch (user.getRole()) {
                case ADMIN -> form = new Form(event, "/views/AdminForm.fxml", "Administrator panel", false);
                case OPERATOR -> {
                }
                case READER -> {
                }
            }

            if (form != null) {
                form.load();
            }

        } catch (Exception e) {
            logger.error("Error during login", e);
            DialogUtils.showError("Error", "An error occurred during login!", e.getMessage());
        }
    }

    @FXML
    public void signUpButtonOnAction(ActionEvent event) {
        Form form = new Form(event, "/views/RegisterForm.fxml", "Registration Form", false);
        form.load();
    }

    @FXML
    public void forgotPasswordLinkOnAction(ActionEvent event) {

    }

    private void checkInput() throws Exception {
        if (usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()) {
            throw new Exception("Please enter username\nand password!");
        }

        if (usernameTextField.getText().isBlank()) {
            throw new Exception("Please enter your username!");
        }

        if (passwordPasswordField.getText().isBlank()) {
            throw new Exception("Please enter your password!");
        }
    }

}