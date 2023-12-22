package com.library.frontend.controllers.base;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.services.LogInService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
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

            LogInService service = (LogInService) ServiceFactory.getService(LogInService.class);

            User user = service.getUser(logInUser);

            switch (user.getRole()) {
                case ADMIN ->{
                    SceneLoader.setUsername(usernameTextField.getText());
                    SceneLoader.load(mouseEvent, "/views/administratorBooksScene.fxml",SceneLoader.getUsername() + " (Administrator)");
                }
                case OPERATOR -> {
                    SceneLoader.load(mouseEvent,"/views/operatorBooksScene.fxml", SceneLoader.getUsername() +" (Operator)");
                }
            }

        } catch (Exception e) {
            logger.error("Error during login", e);
            logInMessageLabel.setText("An error occurred during login!");
        }
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