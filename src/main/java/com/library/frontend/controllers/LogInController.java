package com.library.frontend.controllers;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.SceneLoader;
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

/**
 * The {@code LogInController} class is responsible for handling user login interactions in the library management system.
 * It interacts with the backend {@code LogInService} to authenticate users and load appropriate scenes based on their roles.
 *
 * @see Controller
 */
@NoArgsConstructor
public class LogInController implements Controller {

    @FXML private Button logInButton;
    @FXML public Label logInMessageLabel;
    @FXML public TextField usernameTextField;
    @FXML public PasswordField passwordPasswordField;

    private LogInService service;

    /**
     * Initializes the login controller. Sets focus on the username text field and configures button events.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
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

    /**
     * Handles the action when the login button is clicked or Enter key is pressed.
     * Authenticates the user, sets the user in the SceneLoader, and loads the appropriate scene based on the user's role.
     *
     * @param actionEvent The ActionEvent associated with the login button click.
     */
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

    /**
     * Checks the input fields for correctness. Throws an {@code IncorrectInputException} if any input is missing.
     *
     * @throws IncorrectInputException If the username or password is blank.
     */
    public void checkInput() throws IncorrectInputException {
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
