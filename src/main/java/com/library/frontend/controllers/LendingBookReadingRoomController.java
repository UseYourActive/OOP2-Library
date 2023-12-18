package com.library.frontend.controllers;

import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LendingBookReadingRoomController implements Controller {
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public Button lendButton;

    @FXML public Button cancelButton;
    @FXML public ListView<Book> bookListView;
    @FXML public TreeTableView<Reader> readerTreeTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void searchReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
