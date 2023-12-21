package com.library.frontend.controllers.operator;

import com.library.database.entities.Reader;
import com.library.frontend.controllers.base.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorReadersController implements Controller {
    @FXML public Button booksButton;
    @FXML public Button createReaderButton;
    @FXML public Button removeReaderButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public Button checkReaderInfoButton;
    @FXML public TreeTableView<Reader> readerTreeTableView;
    public TextArea readerTextArea;
    public TableView readerTableView;

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

    public void readerTableViewOnClicked(MouseEvent mouseEvent) {
    }
}
