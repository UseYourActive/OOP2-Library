package com.library.frontend.controllers.admin;

import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.AdministratorBooksDialogService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
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

/**
 * Controller for the dialog that allows administrators to manage books within a specific book inventory.
 */
public class AdministratorBooksDialogController implements Controller {

    @FXML public TableView<Book> bookTableView;
    @FXML public Button closeButton;

    private AdministratorBooksDialogService service;
    private BookTableViewBuilder bookTableViewBuilder;
    private BookInventory bookInventory;

    /**
     * Initializes the controller with necessary services and sets up the book table view.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(AdministratorBooksDialogService.class);

        bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                .findFirst()
                .orElseThrow(RuntimeException::new);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        bookTableViewBuilder = new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        bookTableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());

        bookTableView.setContextMenu(getContextMenu());
    }

    /**
     * Handles the mouse click event on the close button, closing the dialog window.
     */
    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    /**
     * Creates and returns the context menu for book-related actions in the book table view.
     *
     * @return The created ContextMenu.
     */
    private ContextMenu getContextMenu(){
        Map<String, EventHandler<ActionEvent>> menuItems=new HashMap<>();

        menuItems.put("Remove book",this::removeSelectedBooks);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    /**
     * Handles the removal of selected books from the book inventory.
     *
     * @param actionEvent The ActionEvent representing the mouse click event.
     */
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
