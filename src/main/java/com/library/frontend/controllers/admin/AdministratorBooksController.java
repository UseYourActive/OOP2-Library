package com.library.frontend.controllers.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.AdministratorBooksService;
import com.library.database.entities.BookInventory;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.ContextMenuBuilder;
import com.library.utils.tableviews.InventoryTableViewBuilder;
import com.library.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

/**
 * The {@code AdministratorBooksController} class is responsible for managing the user interface interactions related to
 * book administration in the library management system. It interacts with the backend {@code AdministratorBooksService}
 * to handle various book-related operations such as searching, viewing details, registering new books, adding quantities,
 * and removing book inventories.
 *
 * @see Controller
 */
public class AdministratorBooksController implements Controller {

    @FXML public Button operatorsButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    public Button inboxButton;

    private AdministratorBooksService service;
    private TableViewBuilder<BookInventory> bookInventoryTableViewBuilder;


    /**
     * Initializes the AdministratorBooksController by setting up the required services and creating the book inventory
     * table view with context menu options.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = ServiceFactory.getService(AdministratorBooksService.class);

        operatorsButton.requestFocus();
        bookTextArea.setFocusTraversable(false);

        bookInventoryTableViewBuilder = new InventoryTableViewBuilder();
        bookInventoryTableViewBuilder.createTableViewColumns(inventoryTableView);

        inventoryTableView.setContextMenu(getBookInventoryContextMenu());

        bookInventoryTableViewBuilder.updateTableView(inventoryTableView, service.getAllBookInventories());
    }

    /**
     * Handles the action when the "Search" button is clicked. Performs a search based on user input and updates the
     * book inventory table view accordingly.
     */
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

    /**
     * Handles the mouse click event on the "Operators" button. Loads the administrator operators scene and updates the
     * window title with the current administrator's username.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    /**
     * Handles the mouse click event on the book inventory table view. Displays the details of the selected book inventory in
     * the text area. Additionally, double-clicking on an inventory item opens a modality dialog for detailed book
     * information.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        try {
            BookInventory selectedInventory= bookInventoryTableViewBuilder.getSelectedItem(inventoryTableView);

            if(mouseEvent.getButton() == MouseButton.PRIMARY) {

                bookTextArea.setText(selectedInventory.toString());

                if (mouseEvent.getClickCount() == 2) {
                    SceneLoader.loadModalityDialog("/views/admin/administratorBooksDialogScene.fxml", selectedInventory.getRepresentativeBook().getTitle(), selectedInventory);
                    bookInventoryTableViewBuilder.updateTableView(inventoryTableView, service.getAllBookInventories());
                    bookTextArea.clear();
                }
            }

        } catch (NoSuchElementException ignored) {}
    }

    /**
     * Handles the mouse click event on the "Log Out" button. Logs out the current user and returns to the login scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "LogIn");
        }
    }

    /**
     * Handles the mouse click event on the anchor pane. Requests focus for the anchor pane and clears the selection in the
     * inventory table view.
     */
    @FXML
    public void anchorPaneOnMouseClicked() {
        anchorPane.requestFocus();
        inventoryTableView.getSelectionModel().clearSelection();
    }

    /**
     * Returns a context menu with options for book inventory management, including registering new books, adding books,
     * and removing inventories.
     *
     * @return The context menu.
     */
    private ContextMenu getBookInventoryContextMenu() {
        Map<String, EventHandler<ActionEvent>> stringEventHandlerMap=new HashMap<>();
        stringEventHandlerMap.put("Register book",this::registerNewBook);
        stringEventHandlerMap.put("Add books",this::setQuantityOnSelectedBook);
        stringEventHandlerMap.put("Remove inventory",this::removeSelectedInventory);

        return ContextMenuBuilder.prepareContextMenu(stringEventHandlerMap);
    }

    /**
     * Opens the "Register New Book" scene when invoked.
     *
     * @param mouseEvent The ActionEvent representing the mouse click event.
     */
    private void registerNewBook(ActionEvent mouseEvent) {
        SceneLoader.load("/views/admin/registerNewBookScene.fxml", "Register new book");
    }

    /**
     * Removes the selected book inventory after confirming the user's intent through a dialog.
     *
     * @param actionEvent The ActionEvent representing the mouse click event.
     */
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

    /**
     * Opens a modality dialog for adding books to the selected book inventory.
     *
     * @param actionEvent The ActionEvent representing the mouse click event.
     */
    private void setQuantityOnSelectedBook(ActionEvent actionEvent) {
        try {
           BookInventory bookInventory=bookInventoryTableViewBuilder.getSelectedItem(inventoryTableView);
           SceneLoader.loadModalityDialog("/views/admin/addBookQuantityScene.fxml","Add books",bookInventory);

        } catch (NoSuchElementException e) {
            DialogUtils.showInfo("Information", "Please select a book!");
        }
    }

    public void inboxButtonOnMouseClicked(MouseEvent mouseEvent) {
        service.loadNotifications();
    }
}
