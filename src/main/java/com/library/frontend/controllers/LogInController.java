package com.library.frontend.controllers;

import com.library.backend.services.AccessService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LogInController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);
    @FXML private Button logInButton;
    @FXML private Hyperlink forgotPasswordHyperlink;
    @FXML private Label logInMessageLabel;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> usernameTextField.requestFocus());
    }
    @FXML
    public void logInButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            checkInput();

            User logInUser = User.builder()
                    .username(usernameTextField.getText())
                    .password(passwordPasswordField.getText())
                    .build();

            AccessService service = (AccessService) ServiceFactory.getService(AccessService.class);

            User user = service.getUser(logInUser);

            switch (user.getRole()) {
                case ADMIN ->{
                    SceneLoader.load(mouseEvent, "/views/administratorBooksScene.fxml",usernameTextField.getText() + " (Administrator)");
                }
                case OPERATOR -> {
                    SceneLoader.load(mouseEvent,"/views/operatorBooksScene.fxml", usernameTextField.getText()+" (Operator)");
                }
            }

        } catch (Exception e) {
            logger.error("Error during login", e);
            DialogUtils.showError("Error", "An error occurred during login!", e.getMessage());
        }
    }
    @FXML
    public void forgotPasswordHyperlinkOnMouseClicked(MouseEvent mouseEvent) {
    }


    //Try to move the logic inside a class
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