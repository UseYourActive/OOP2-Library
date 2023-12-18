package com.library.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorBooksController implements Controller{
    @FXML public Button readersButton;
    @FXML public Button archiveButton;
    @FXML public Button lendButton;
    @FXML public Button lendReadingRoomButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchBookButton;
    @FXML public TreeView booksReadersTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void archiveButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void lendReadingRoomButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
