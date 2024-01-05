package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.trying.BookDialogService;
import com.library.backend.services.trying.ContextMenuService;
import com.library.database.entities.Book;
import com.library.frontend.controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorBooksDialogController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public TableView<Book> bookTableView;
    @FXML public Button closeButton;

    private BookDialogService bookDialogService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookDialogService = new BookDialogService();

        bookTableView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        bookDialogService.initialize(bookTableView);

        ContextMenu contextMenu = ContextMenuService.createBookContextMenu(
                bookTableView,
                this::archiveSelectedBooks,
                this::removeSelectedBooks
        );
        bookTableView.setContextMenu(contextMenu);
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        bookDialogService.closeDialog(closeButton);
    }

    @FXML
    public void archiveSelectedBooks(ActionEvent actionEvent) {
        bookDialogService.archiveSelectedBooks(bookTableView);
    }

    @FXML
    public void removeSelectedBooks(ActionEvent actionEvent) {
        bookDialogService.removeSelectedBooks(bookTableView, closeButton);
    }
}
