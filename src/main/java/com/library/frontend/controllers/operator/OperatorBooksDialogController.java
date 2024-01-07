package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OperatorBooksDialogController implements Controller {

    @FXML private Button closeButton;
    @FXML private TableView<Book> bookTableView;

    private TableViewBuilder<Book> tableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableViewBuilder = new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        BookInventory bookInventory = (BookInventory) SceneLoader.getTransferableObjects()[0];

        List<Book> bookList = bookInventory.getBookList();

        tableViewBuilder.updateTableView(bookTableView, bookList);
    }

    @FXML
    private void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
