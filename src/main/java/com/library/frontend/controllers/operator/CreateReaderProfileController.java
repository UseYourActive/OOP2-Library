package com.library.frontend.controllers.operator;

import com.google.common.collect.Lists;
import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Reader;
import com.library.database.enums.ReaderRating;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateReaderProfileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreateReaderProfileController.class);
    @FXML public TextField firstNameTextField;
    @FXML public TextField middleNameTextField;
    @FXML public TextField lastNameTextField;
    @FXML public TextField phoneNumberTextField;
    @FXML public TextField emailTextField;
    @FXML public Button createReaderProfileButton;
    @FXML public Button cancelButton;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);
    }

    @FXML
    public void createReaderProfileButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            String firstName = firstNameTextField.getText();
            String middleName = middleNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();

            Reader reader = Reader.builder()
                    .firstName(firstName)
                    .middleName(middleName)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .bookForms(Lists.newArrayList())
                    .rating(ReaderRating.THREE_STAR)
                    .build();

            operatorService.createReader(reader);
        } catch (Exception e) {
            logger.error("Error occurred during creating reader profile", e);
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Operator readers scene");
        } catch (Exception e) {
            logger.error("Error occurred during canceling creating reader profile", e);
        }
    }
}
