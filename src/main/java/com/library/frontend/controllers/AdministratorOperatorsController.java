package com.library.frontend.controllers;

import com.library.database.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller{
    @FXML public Button booksButton;
    @FXML public Button registerBookButton;
    @FXML public Button loadBooksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TreeView<Book> bookTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void booksButtonOnAction(ActionEvent event) {
    }

    public void registerBookButtonOnAction(ActionEvent event) {
    }

    public void loadBooksButtonOnAction(ActionEvent event) {
    }
}
