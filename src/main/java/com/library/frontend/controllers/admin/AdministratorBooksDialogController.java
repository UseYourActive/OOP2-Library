package com.library.frontend.controllers.admin;

import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.AdministratorBooksDialogControllerService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.frontend.SceneLoader;
import com.library.utils.tableviews.BookTableViewBuilder;
import com.library.utils.tableviews.ContextMenuBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class AdministratorBooksDialogController implements Controller {

    @FXML public TableView<Book> bookTableView;
    @FXML public Button closeButton;

    private AdministratorBooksDialogControllerService service;
    private BookTableViewBuilder bookTableViewBuilder;
    private BookInventory bookInventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(AdministratorBooksDialogControllerService.class);

        bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                .findFirst()
                .orElseThrow(RuntimeException::new);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        bookTableViewBuilder = new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        bookTableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());

        bookTableView.setContextMenu(getContextMenu());
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    private ContextMenu getContextMenu(){
        Map<String, EventHandler<ActionEvent>> menuItems=new HashMap<>();

        menuItems.put("Remove book",this::removeSelectedBooks);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    private void removeSelectedBooks(ActionEvent actionEvent) {

            try {
                List<Book> booksToRemove = bookTableViewBuilder.getSelectedItems(bookTableView);

                if (!booksToRemove.isEmpty()) {

                    if (!booksToRemove.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.DAMAGED) || book.getBookStatus().equals(BookStatus.ARCHIVED))) {
                        DialogUtils.showInfo("Information", "Please choose only ARCHIVED or DAMAGED books");
                    } else {
                        if (booksToRemove.size() == bookInventory.getBookList().size()) {
                            if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete\nall books from from inventory?\nThis will resolve to removing the inventory itself")) {
                                service.removeSelectedBooks(bookInventory, booksToRemove);

                                ((Stage) closeButton.getScene().getWindow()).close();
                            }
                        } else {
                            if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want\nto delete the selected books ?")) {
                                service.removeSelectedBooks(bookInventory, booksToRemove);

                                bookTableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());
                            }
                        }
                    }
                } else {
                    DialogUtils.showInfo("Information", "Please select an inventory!");
                }
            }catch (NoSuchElementException ignored){}

    }
}
