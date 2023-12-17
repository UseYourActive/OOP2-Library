package com.library.frontend.controllers;

import com.library.database.entities.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller{
    public Button booksButton;
    public Button registerBookButton;
    public Button loadBooksButton;
    public TextField searchBookTextField;
    public Button searchBookButton;
    public TreeView<Book> bookTreeView;

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
