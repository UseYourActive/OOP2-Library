package com.library.frontend.controllers.operator;

import com.library.frontend.controllers.base.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateReaderProfileController implements Controller {
    @FXML public TextField firstNameTextField;
    @FXML public TextField middleNameTextField;
    @FXML public TextField lastNameTextField;
    @FXML public TextField phoneNumberTextField;
    @FXML public TextField emailTextField;
    @FXML public Button createReaderProfileButton;

    @FXML public Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void createReaderProfileButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
