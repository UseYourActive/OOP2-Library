package com.library.frontend.controllers.operator;

import com.library.database.entities.Book;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ReaderInfoController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ReaderInfoController.class);

    @FXML public TreeView<Book> bookFormTreeView;
    @FXML public Button goBackButton;
    @FXML public Text readerNamesText;
    @FXML public Text phoneNumberText;
    @FXML public Text emailText;
    @FXML public Rating readerRatingRating;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void goBackButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Operator readers scene");
    }
}
