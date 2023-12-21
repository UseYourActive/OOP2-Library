package com.library.frontend.controllers.operator;

import com.library.database.entities.Book;
import com.library.frontend.controllers.base.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class ReaderInfoController implements Controller {
    @FXML public TreeView<Book> bookFormTreeView;
    @FXML
    public Button goBackButton;
    @FXML public Text readerNamesText;
    @FXML public Text phoneNumberText;
    @FXML public Text emailText;
    @FXML public Rating readerRatingRating;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    @FXML
    public void goBackButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
