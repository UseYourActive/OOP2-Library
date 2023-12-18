package com.library.frontend.controllers;

import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateLendingBookFormController implements Controller {
    @FXML public TreeView<Book> bookTreeView;
    @FXML public TreeView<Reader> readerTreeView;
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button searchButton;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
