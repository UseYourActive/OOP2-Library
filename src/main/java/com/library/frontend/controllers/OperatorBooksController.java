package com.library.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorBooksController implements Controller{
    @FXML public Button readersButton;
    @FXML public Button archiveButton;
    @FXML public Button lendButton;
    @FXML public Button lendReadingRoomButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchBookButton;

    @FXML public TreeTableView booksTreeTableView;

    @Setter
    private String stageTittle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void archiveButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void lendReadingRoomButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
