package com.library.frontend.controllers;

import com.library.database.entities.Book;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReaderInfoController implements Controller {
    public TreeView<Book> bookFormTreeView;
    public Button goBackButton;
    public Text readerNamesText;
    public Text phoneNumberText;
    public Text emailText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
