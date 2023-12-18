package com.library.frontend.controllers;

import com.library.database.entities.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorReadersController implements Controller {
    @FXML public Button booksButton;
    @FXML public Button createReaderButton;
    @FXML public Button removeReaderButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TreeView<Reader> readerTreeView;
    @FXML public Button checkReaderInfoButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void createReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void removeReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void searchReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void checkReaderInfoButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
