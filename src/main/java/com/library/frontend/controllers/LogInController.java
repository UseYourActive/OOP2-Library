package com.library.frontend.controllers;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
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
    private LogInService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> usernameTextField.requestFocus());
        service = (LogInService) ServiceFactory.getService(LogInService.class);
    }

    @FXML
    public void logInButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                checkInput();

                User logInUser = User.builder()
                        .username(usernameTextField.getText())
                        .password(passwordPasswordField.getText())
                        .build();

                User user = service.getUser(logInUser);
                SceneLoader.setUsername(usernameTextField.getText());

                switch (user.getRole()) {
                    case ADMIN -> {
                        SceneLoader.load(mouseEvent, "/views/admin/administratorBooksScene.fxml", SceneLoader.getUsername() + " (Administrator)");
                    }
                    case OPERATOR -> {
                        SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", SceneLoader.getUsername() + " (Operator)");
                    }
                }

            } catch (UserNotFoundException e) {
                logger.error(e.getMessage());
                logInMessageLabel.setText("User not found!");
            } catch (HibernateException e) {
                logger.error(e.getMessage());
                logInMessageLabel.setText("Error loading the database!");
            } catch (IncorrectInputException e) {
                logger.error(e.getMessage());
                logInMessageLabel.setText("Invalid user input!");
            }
        }
    }

    //Try to move the logic inside a class
    private void checkInput() throws IncorrectInputException {
        if (usernameTextField.getText().isBlank() && passwordPasswordField.getText().isBlank()) {
            throw new IncorrectInputException("Please enter username\nand password!");
        }

        if (usernameTextField.getText().isBlank()) {
            throw new IncorrectInputException("Please enter your username!");
        }

        if (passwordPasswordField.getText().isBlank()) {
            throw new IncorrectInputException("Please enter your password!");
        }
    }

    public void logInButtonOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            //logInButtonOnMouseClicked((MouseEvent) keyEvent);
        }
    }
}