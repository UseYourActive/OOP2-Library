package com.library.frontend.controllers;

import com.library.backend.operations.OperationFactory;
import com.library.backend.operations.processors.LogInOperationProcessor;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
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

import static com.library.database.enums.Role.*;

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

            LogInRequest request = LogInRequest.builder()
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .build();

            LogInOperationProcessor createUserOperationProcessor = OperationFactory.getOperationProcessor(LogInOperationProcessor.class);

            LogInResponse response = createUserOperationProcessor.process(request);


            Form form = null;
            switch (response.getRole()) {
                case ADMIN -> form = new Form(event, "/views/AdminForm.fxml", "Administrator panel", false);
                case OPERATOR -> {
                }
                case READER -> {
                }
            }

            if (form != null) {
                form.load();
            } else {
                throw new Exception("Unknown role or error processing login response");
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