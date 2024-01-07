package com.library.frontend.controllers.operator;

import com.google.common.collect.Lists;
import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Reader;
import com.library.database.entities.ReaderRating;
import com.library.database.enums.Ratings;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML public Label infoLabel;

    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);
    }

    @FXML
    public void createReaderProfileButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            checkInput();

            String firstName = firstNameTextField.getText();
            String middleName = middleNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText();

            ReaderRating readerRating= ReaderRating.builder()
                    .rating(Ratings.NONE)
                    .currentValue(-1)
                    .coefficient(0)
                    //.readers(new ArrayList<>())
                    .build();


            Reader reader = Reader.builder()
                    .firstName(firstName)
                    .middleName(middleName)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .bookForms(Lists.newArrayList())
                    .readerRating(readerRating)
                    .build();

           // readerRating.getReaders().add(reader);

            //operatorService.saveRating(readerRating);
            operatorService.createReader(reader);
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", SceneLoader.getUser().getUsername() + "(Operator)");
        } catch (IncorrectInputException e) {
            infoLabel.setText(e.getMessage());
            //logger.error("Error occurred during creating reader profile", e);
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Operator readers scene");
        }
    }

    private void checkInput() throws IncorrectInputException {
        if (firstNameTextField.getText().isEmpty())
            throw new IncorrectInputException("Please enter first name.");

        if (middleNameTextField.getText().isEmpty())
            throw new IncorrectInputException("Please enter middle name.");

        if (lastNameTextField.getText().isEmpty())
            throw new IncorrectInputException("Please enter last name.");

        if (phoneNumberTextField.getText().isEmpty())
            throw new IncorrectInputException("Please enter phone number.");

    }
}
