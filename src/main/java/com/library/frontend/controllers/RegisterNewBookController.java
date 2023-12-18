package com.library.frontend.controllers;

import com.library.database.enums.Genre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterNewBookController implements Controller {
    @FXML public TextField ISBNTextField;
    @FXML public TextField titleTextField;
    @FXML public TextField authorTextField;
    @FXML public TextField yearTextField;
    @FXML public TextArea resumeTextField;
    @FXML public ComboBox<Genre> genreComboBox;
    @FXML public Button registerButton;

    @FXML public Button cancelButton;
    @FXML public Label informationLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
