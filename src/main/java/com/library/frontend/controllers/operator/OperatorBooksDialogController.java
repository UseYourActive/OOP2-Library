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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OperatorBooksDialogController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksDialogController.class);
    @FXML private Button closeButton;
    @FXML private TableView<Book> bookTableView;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //prepareContextMenu();
        TableViewBuilder<Book> tableViewBuilder = new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        BookInventory bookInventory= (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error idk"));

        List<Book> bookList= bookInventory.getBookList();

        updateTableView(bookList);
    }

    @FXML
    private void bookTableViewOnMouseClicked(MouseEvent mouseEvent) {

    }

    @FXML
    private void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        ((Stage)closeButton.getScene().getWindow()).close();
    }

    private void updateTableView(List<Book> bookList) {
        try {
            bookTableView.getItems().clear();
            bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }

//    private void prepareContextMenu(){
//        try {
//            ContextMenu contextMenu = new ContextMenu();
//
//            MenuItem removeBookItem = new MenuItem("Remove book");
//
//            contextMenu.getItems().add(removeBookItem);
//
//            bookTableView.setContextMenu(contextMenu);
//
//            removeBookItem.setOnAction(this::removeSelectedBooks);
//
//        } catch (Exception e) {
//            logger.error("Error occurred during context menu preparation", e);
//        }
//    }
}
