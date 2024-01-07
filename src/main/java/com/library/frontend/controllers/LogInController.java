package com.library.frontend.controllers;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LogInController implements Controller {

    @FXML private Button logInButton;
    @FXML private Label logInMessageLabel;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPasswordField;

    private LogInService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> usernameTextField.requestFocus());
        service = ServiceFactory.getService(LogInService.class);

        logInButton.setOnMouseClicked(event -> {
            if (event.getButton().name().equals("PRIMARY")) {
                logInButton.fire(); // Simulate button click
            }
        });

        logInButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logInButton.fire(); // Simulate button click
            }
        });
    }

    @FXML
    public void logInButtonOnAction(ActionEvent actionEvent) {
        try {
            checkInput();

            String username = usernameTextField.getText();
            String password = passwordPasswordField.getText();

            User user = service.getUser(username, password);
            SceneLoader.setUser(user);

            switch (user.getRole()) {
                case ADMIN ->
                        SceneLoader.load(actionEvent, "/views/admin/administratorBooksScene.fxml", user.getUsername() + "(Administrator)");

                case OPERATOR ->
                        SceneLoader.load(actionEvent, "/views/operator/operatorBooksScene.fxml", user.getUsername() + "(Operator)");

            }

        } catch (UserNotFoundException e) {
            logInMessageLabel.setText("User not found!");
        } catch (HibernateException e) {
            logInMessageLabel.setText("Error loading the database!");
        } catch (IncorrectInputException e) {
            logInMessageLabel.setText("Invalid user input!");
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
}