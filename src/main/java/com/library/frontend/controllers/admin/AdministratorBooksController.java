package com.library.frontend.controllers.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.admin.AdministratorBooksControllerService;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.ContextMenuBuilder;
import com.library.frontend.utils.tableviews.InventoryTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class AdministratorBooksController implements Controller {

    @FXML public Button operatorsButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;

    private AdministratorBooksControllerService service;
    private TableViewBuilder<BookInventory> bookInventoryTableViewBuilder;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = new AdministratorBooksControllerService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance());

        operatorsButton.requestFocus();
        bookTextArea.setFocusTraversable(false);

        bookInventoryTableViewBuilder = new InventoryTableViewBuilder();
        bookInventoryTableViewBuilder.createTableViewColumns(inventoryTableView);

        inventoryTableView.setContextMenu(getBookInventoryContextMenu());

        bookInventoryTableViewBuilder.updateTableView(inventoryTableView, service.getAllBookInventories());
    }

    @FXML
    public void searchBookButtonOnMouseClicked() {
        try {
            String stringToSearch = searchBookTextField.getText();
            Collection<BookInventory> results = service.searchBookInventory(stringToSearch);
            bookInventoryTableViewBuilder.updateTableView(inventoryTableView,results.stream().toList());

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
                    bookInventoryTableViewBuilder.updateTableView(inventoryTableView, service.getAllBookInventories());
                    bookTextArea.clear();
                }
            }

        } catch (NoSuchElementException ignored) {}
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "LogIn");
        }
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        anchorPane.requestFocus();
        inventoryTableView.getSelectionModel().clearSelection();
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
       BookInventory inventory = inventoryTableView.getSelectionModel().getSelectedItem();

       if (inventory != null) {
           if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete these book/s from the database ?")) {

               service.removeInventory(inventory);

               bookInventoryTableViewBuilder.updateTableView(inventoryTableView, service.getAllBookInventories());
               bookTextArea.clear();
           }
       } else {
           DialogUtils.showInfo("Information", "Please select an inventory!");
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
