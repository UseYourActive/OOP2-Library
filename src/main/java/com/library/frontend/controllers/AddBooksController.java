package com.library.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBooksController implements Controller {
    @FXML public Button addButton;
    @FXML public Button cancelButton;
    @FXML public TextField amountTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
