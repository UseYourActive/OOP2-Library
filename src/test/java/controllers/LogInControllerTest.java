package controllers;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.controllers.LogInController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class LogInControllerTest {

    @InjectMocks
    private LogInController logInController;

    @Mock
    private LogInService logInService;

    @Mock
    private ActionEvent actionEvent;

    @BeforeEach
    void setUp() throws IOException {
        // Load FXML and set the controller manually
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/logInScene.fxml"));
        loader.setController(logInController);
        Parent root = loader.load();

        // Create a scene and set it to the stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        // Show the stage (this is necessary for TestFX interactions)
        Platform.runLater(stage::showAndWait);

        // Perform other setup as needed
        MockitoAnnotations.openMocks(this);
    }
}

