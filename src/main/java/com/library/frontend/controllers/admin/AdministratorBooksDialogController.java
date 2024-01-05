package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorBooksDialogController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public TableView<Book> bookTableView;
    @FXML public Button closeButton;

    private AdminService adminService;
    private BookInventory bookInventory;
    private BookTableViewBuilder tableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService = ServiceFactory.getService(AdminService.class);

        bookInventory= (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects()).findFirst().orElseThrow(RuntimeException::new);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        prepareContextMenu();

        tableViewBuilder = new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        BookInventory bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects()).findFirst().orElseThrow(RuntimeException::new);

        List<Book> bookList = bookInventory.getBookList();

        tableViewBuilder.updateTableView(bookTableView,bookList);
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem archiveBook = new MenuItem("Archive book");
            MenuItem removeBook = new MenuItem("Remove book");

            contextMenu.getItems().add(archiveBook);
            contextMenu.getItems().add(removeBook);

            archiveBook.setOnAction(this::archiveSelectedBooks);
            removeBook.setOnAction(this::removeSelectedBooks);

            bookTableView.setContextMenu(contextMenu);

        } catch (Exception e) {
            logger.error("Error occurred during context menu preparation", e);
        }
    }

    private void archiveSelectedBooks(ActionEvent actionEvent){
        try {
            List<Book> booksToArchive = bookTableView.getSelectionModel().getSelectedItems();

            if(!booksToArchive.isEmpty()){

                if(!booksToArchive.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.AVAILABLE))){
                    DialogUtils.showInfo("Information","Please choose only AVAILABLE books");
                }
                else if(DialogUtils.showConfirmation("Archiving books","Are you sure you want to archive selected book/s ?")) {

                    for (Book book : booksToArchive) {
                        book.setBookStatus(BookStatus.ARCHIVED);
                        adminService.saveBook(book);
                    }

                    tableViewBuilder.updateTableView(bookTableView,adminService.getAllBookInventories().stream().filter(bI ->bI.equals(bookInventory)).findFirst().orElseThrow().getBookList());
                }
            }

        }catch (Exception e){
            logger.error("Error occurred during removing selected books", e);
        }
    }

    private void removeSelectedBooks(ActionEvent actionEvent) {
        try {
            List<Book> booksToRemove = bookTableView.getSelectionModel().getSelectedItems();

            if (!booksToRemove.isEmpty()) {

                if(!booksToRemove.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.AVAILABLE)||book.getBookStatus().equals(BookStatus.ARCHIVED))){
                    DialogUtils.showInfo("Information","Please choose only AVAILABLE books");
                }else{

                    if (booksToRemove.size() == bookInventory.getBookList().size()) {
                        if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete\nall books from from inventory?\nThis will resolve to removing the inventory itself")) {
                            updateBookForms(booksToRemove);
                            removeBooks(bookInventory, booksToRemove);
                            ((Stage) closeButton.getScene().getWindow()).close();//close scene
                        }
                    } else {
                        if(DialogUtils.showConfirmation("Confirmation", "Are you sure you want\nto delete the selected books ?")) {
                            updateBookForms(booksToRemove);
                            removeBooks(bookInventory, booksToRemove);
                            tableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());// updates tableView
                        }
                    }
                }
            } else {
                DialogUtils.showInfo("Information", "Please select an inventory!");
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected books", e);
        }
    }

    private void updateBookForms(List<Book> books){

       for(BookForm bookForm:adminService.getAllBookForms()){
            for(Book bookToRemove:books){
                bookForm.getBooks().remove(bookToRemove);
            }
            adminService.saveBookForm(bookForm);
       }
    }

    private void removeBooks(BookInventory bookInventory, List<Book> booksToRemove) {
        boolean flag = true;

        for (Book bookToRemove : booksToRemove) {
            if (bookInventory.getRepresentiveBook().equals(bookToRemove) && bookInventory.getBookList().size() == 1) {
                adminService.removeInventory(bookInventory);
                tableViewBuilder.updateTableView(bookTableView,bookInventory.getBookList());
                flag = false;
                break;
            }

            if (bookInventory.getRepresentiveBook().equals(bookToRemove)) {
                List<Book> nonSelected = bookInventory.getBookList().stream()
                        .filter(book -> !booksToRemove.contains(book))
                        .toList();

                for (Book book : nonSelected) {
                    if (!book.equals(bookInventory.getRepresentiveBook())) {
                        bookInventory.setRepresentiveBook(book);
                        break;
                    }
                }
            }

            bookInventory.getBookList().remove(bookToRemove);
        }

        if (flag)
            adminService.saveInventory(bookInventory);
    }

}
