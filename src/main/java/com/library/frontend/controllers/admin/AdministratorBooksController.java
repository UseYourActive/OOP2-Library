package com.library.frontend.controllers.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.frontend.utils.tableviews.ContextMenuBuilder;
import com.library.frontend.utils.tableviews.InventoryTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class AdministratorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public Button operatorsButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;

    private AdminService adminService;
    private TableViewBuilder<BookInventory> bookInventoryTableViewBuilder;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService = ServiceFactory.getService(AdminService.class);


        operatorsButton.requestFocus();
        bookTextArea.setFocusTraversable(false);

        bookInventoryTableViewBuilder = new InventoryTableViewBuilder();
        bookInventoryTableViewBuilder.createTableViewColumns(inventoryTableView);

        inventoryTableView.setContextMenu(getBookInventoryContextMenu());

        updateTableView(adminService.getAllBookInventories());
    }

    @FXML
    public void searchBookButtonOnMouseClicked() {
        try {
            String stringToSearch = searchBookTextField.getText();
            Collection<BookInventory> results = adminService.searchBookInventory(stringToSearch);
            updateTableView(results.stream().toList());
        }catch (SearchEngineException e){
            DialogUtils.showInfo("Error ","BookInventory not found!");
        }
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        try {
            BookInventory selectedInventory= bookInventoryTableViewBuilder.getSelectedItem(inventoryTableView);

            if(mouseEvent.getButton() == MouseButton.PRIMARY) {

                bookTextArea.setText(selectedInventory.toString());

                if (mouseEvent.getClickCount() == 2) {
                    SceneLoader.loadModalityDialog("/views/admin/administratorBooksDialogScene.fxml", selectedInventory.getRepresentiveBook().getTitle(), selectedInventory);
                    bookInventoryTableViewBuilder.updateTableView(inventoryTableView, adminService.getAllBookInventories());
                    bookTextArea.clear();
                }
            }

        } catch (NoSuchElementException ignored) {}
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "LogIn");
            }
        } catch (Exception e) {
            logger.error("Error occurred during logout", e);
        }
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        try {
            anchorPane.requestFocus();
            inventoryTableView.getSelectionModel().clearSelection();
        } catch (Exception e) {
            logger.error("Error occurred during handling anchor pane click", e);
        }
    }

    private void updateTableView(List<BookInventory> inventories) {
        try {
            inventoryTableView.getItems().clear();

            inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }

    private ContextMenu getBookInventoryContextMenu() {
        Map<String, EventHandler<ActionEvent>> stringEventHandlerMap=new HashMap<>();
        stringEventHandlerMap.put("Register book",this::registerNewBook);
        stringEventHandlerMap.put("Add books",this::setQuantityOnSelectedBook);
        stringEventHandlerMap.put("Remove inventory",this::removeSelectedInventory);

        return ContextMenuBuilder.prepareContextMenu(stringEventHandlerMap);
    }

    private void registerNewBook(ActionEvent mouseEvent) {
        SceneLoader.load("/views/admin/registerNewBookScene.fxml", "Register new book");
    }


    private void removeSelectedInventory(ActionEvent actionEvent) {
        try {
            BookInventory inventory = inventoryTableView.getSelectionModel().getSelectedItem();

            if (inventory != null) {
                if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete these book/s from the database ?")) {

                    updateBookForms(inventory.getBookList());

                    adminService.removeInventory(inventory);

                    updateTableView(adminService.getAllBookInventories());
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

    private void setQuantityOnSelectedBook(ActionEvent actionEvent) {
        try {
           BookInventory bookInventory=bookInventoryTableViewBuilder.getSelectedItem(inventoryTableView);
           SceneLoader.loadModalityDialog("/views/admin/addBookQuantityScene.fxml","Add books",bookInventory);

        } catch (NoSuchElementException e) {
            DialogUtils.showInfo("Information", "Please select a book!");
        }
    }
}
