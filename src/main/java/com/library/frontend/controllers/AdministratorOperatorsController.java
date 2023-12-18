package com.library.frontend.controllers;

import com.library.database.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller{
    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public TreeView<Book> bookTreeView;
    @FXML public Button createOperatorButton;
    @FXML public Button removeOperatorButton;
    @FXML public Button searchOperatorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void registerBookButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void loadBooksButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void searchOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void removeOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
