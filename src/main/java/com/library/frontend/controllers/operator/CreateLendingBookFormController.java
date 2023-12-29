package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateLendingBookFormController implements Controller {
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public Button searchReaderButton;
    @FXML public ListView<Book> bookListView;
    @FXML public TreeTableView<Reader> readerTreeTableView;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        bookListView.getItems().addAll(operatorService.getAllBooks());
    }
    @FXML
    public void searchReaderButtonOnMouseClicked(MouseEvent mouseEvent) {

    }
    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", "Operator books scene");
    }
}
