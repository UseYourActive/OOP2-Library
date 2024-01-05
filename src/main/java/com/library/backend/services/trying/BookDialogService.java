package com.library.backend.services.trying;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class BookDialogService { // AdministratorBooksDialogController
    private static final Logger logger = LoggerFactory.getLogger(BookDialogService.class);

    private final AdminService adminService;
    private BookTableViewBuilder tableViewBuilder;

    public BookDialogService() {
        this.adminService = ServiceFactory.getService(AdminService.class);
    }

    public void initialize(TableView<Book> bookTableView) {
        BookInventory bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                .findFirst()
                .orElseThrow(RuntimeException::new);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableViewBuilder = new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        List<Book> bookList = bookInventory.getBookList();
        tableViewBuilder.updateTableView(bookTableView, bookList);
    }

    public void closeDialog(Button closeButton) {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    public void archiveSelectedBooks(TableView<Book> bookTableView) {
        try {
            List<Book> booksToArchive = tableViewBuilder.getSelectedItems(bookTableView);


            if (!booksToArchive.isEmpty()) {
                BookInventory bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);

                if (!booksToArchive.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.AVAILABLE))) {
                    DialogUtils.showInfo("Information", "Please choose only AVAILABLE books");
                } else if (DialogUtils.showConfirmation("Archiving books", "Are you sure you want to archive selected book/s ?")) {
                    for (Book book : booksToArchive) {
                        book.setBookStatus(BookStatus.ARCHIVED);
                        adminService.saveBook(book);
                    }

                    tableViewBuilder.updateTableView(bookTableView, adminService.getAllBookInventories().stream()
                            .filter(bI -> bI.equals(bookInventory))
                            .findFirst()
                            .orElseThrow()
                            .getBookList());
                }
            }
        }catch (NoSuchElementException ignored){}
    }

    public void removeSelectedBooks(TableView<Book> bookTableView, Button closeButton) {
        try {
            List<Book> booksToRemove = tableViewBuilder.getSelectedItems(bookTableView);

            if (!booksToRemove.isEmpty()) {
                BookInventory bookInventory = (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);

                if (!booksToRemove.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.DAMAGED) || book.getBookStatus().equals(BookStatus.ARCHIVED))) {
                    DialogUtils.showInfo("Information", "Please choose only ARCHIVED or damaged books");
                } else {
                    if (booksToRemove.size() == bookInventory.getBookList().size()) {
                        if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete\nall books from from inventory?\nThis will resolve to removing the inventory itself")) {
                            updateBookForms(booksToRemove);
                            removeBooks(bookInventory, booksToRemove, bookTableView);
                            ((Stage) closeButton.getScene().getWindow()).close();
                        }
                    } else {
                        if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want\nto delete the selected books ?")) {
                            updateBookForms(booksToRemove);
                            removeBooks(bookInventory, booksToRemove, bookTableView);
                            BookTableViewBuilder tableViewBuilder = new BookTableViewBuilder();
                            tableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());
                        }
                    }
                }
            } else {
                DialogUtils.showInfo("Information", "Please select an inventory!");
            }
        }catch (NoSuchElementException ignored){}
    }

    private void updateBookForms(List<Book> books) {
        for (BookForm bookForm : adminService.getAllBookForms()) {
            for (Book bookToRemove : books) {
                bookForm.getBooks().remove(bookToRemove);
            }
            adminService.saveBookForm(bookForm);
        }
    }

    private void removeBooks(BookInventory bookInventory, List<Book> booksToRemove, TableView<Book> bookTableView) {
        boolean flag = true;

        for (Book bookToRemove : booksToRemove) {
            if (bookInventory.getRepresentiveBook().equals(bookToRemove) && bookInventory.getBookList().size() == 1) {
                adminService.removeInventory(bookInventory);
                BookTableViewBuilder tableViewBuilder = new BookTableViewBuilder();
                tableViewBuilder.updateTableView(bookTableView, bookInventory.getBookList());
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
