package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService= ServiceFactory.getService(AdminService.class);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        prepareContextMenu();
        TableViewBuilder<Book> tableViewBuilder=new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        BookInventory bookInventory= (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects()).findFirst().orElseThrow(RuntimeException::new);

        List<Book> bookList= bookInventory.getBookList();

        updateTableView(bookList);
    }


    @FXML
    public void bookTableViewOnMouseClicked() {

    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage)closeButton.getScene().getWindow()).close();
    }

    private void updateTableView(List<Book> bookList){
        try {
            bookTableView.getItems().clear();
            bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }

    private void prepareContextMenu(){
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem removeBookItem = new MenuItem("Remove book");

            contextMenu.getItems().add(removeBookItem);

            bookTableView.setContextMenu(contextMenu);

            removeBookItem.setOnAction(this::removeSelectedBooks);

        } catch (Exception e) {
            logger.error("Error occurred during context menu preparation", e);
        }
    }

    private void removeSelectedBooks(ActionEvent actionEvent) {
        try {
            List<Book> booksToRemove = bookTableView.getSelectionModel().getSelectedItems();

            BookInventory bookInventory= (BookInventory) Arrays.stream(SceneLoader.getTransferableObjects()).findFirst().orElseThrow(RuntimeException::new);

            if (!booksToRemove.isEmpty()) {

                if(booksToRemove.size()==bookInventory.getBookList().size()){
                    if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete\nall books from from inventory?\nThis will resolve to removing the inventory itself")){
                        removeBooks(bookInventory,booksToRemove);
                        ((Stage)closeButton.getScene().getWindow()).close();//close scene
                    }
                }else {
                    removeBooks(bookInventory,booksToRemove);
                    updateTableView(bookInventory.getBookList());// updates tableView
               }
            } else {
                DialogUtils.showInfo("Information", "Please select an inventory!");
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected books", e);
        }
    }

    private void removeBooks(BookInventory bookInventory,List<Book> booksToRemove){
        boolean flag=true;

        for(Book bookToRemove:booksToRemove)
        {
            if(bookInventory.getRepresentiveBook().equals(bookToRemove)&&bookInventory.getBookList().size()==1){
                adminService.removeInventory(bookInventory);
                updateTableView(bookInventory.getBookList());
                flag=false;
                break;
            }

            if(bookInventory.getRepresentiveBook().equals(bookToRemove)){
                List<Book> nonSelected = bookInventory.getBookList().stream()
                        .filter(book -> !booksToRemove.contains(book))
                        .toList();

                for(Book book:nonSelected){
                    if(!book.equals(bookInventory.getRepresentiveBook())){
                        bookInventory.setRepresentiveBook(book);
                        break;
                    }
                }
            }

            bookInventory.getBookList().remove(bookToRemove);

        }

        if(flag)
            adminService.saveInventory(bookInventory);
    }

}
