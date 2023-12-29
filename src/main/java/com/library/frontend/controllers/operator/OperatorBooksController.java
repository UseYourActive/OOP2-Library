package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.InventoryTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OperatorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksController.class);
    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public TextArea bookTextArea;
    @FXML public AnchorPane anchorPane;
    @FXML private Button logOutButton;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        readersButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder<BookInventory> bookInventoryTableViewBuilder = new InventoryTableViewBuilder();
        bookInventoryTableViewBuilder.createTableViewColumns(inventoryTableView);

        updateTableView(operatorService.getAllBookInventories());

        prepareContextMenu();
    }

    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Readers");
        }
    }

    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                List<BookInventory> results = new ArrayList<>();
                List<BookInventory> inventories = operatorService.getAllBookInventories();
                String stringToSearch = searchBookTextField.getText();
                if (stringToSearch.isEmpty()) {
                    updateTableView(inventories);
                } else {

                    for (BookInventory inventory : inventories) {
                        Book book = inventory.getBookList().get(0);

                        if (book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(inventory);

                        if (book.getAuthor().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(inventory);

                        if (book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(inventory);

                        if (book.getGenre().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(inventory);

                        if (book.getPublishYear() != null && book.getPublishYear().toString().contains(stringToSearch))
                            results.add(inventory);
                    }

                    updateTableView(results);
                }
            } catch (Exception e) {
                logger.error("Error occurred during searching books", e);
            }
        }
    }

    @FXML
    public void bookTableViewOnClicked() {
        try {
            BookInventory selectedInventory = inventoryTableView.getSelectionModel().getSelectedItem();

            if (selectedInventory != null)
                bookTextArea.setText(selectedInventory.toString());
        } catch (Exception e) {
            logger.error("Error occurred during processing book table view click", e);
        }
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem archiveItem = new MenuItem("Archive book");
            MenuItem lendBookItem = new MenuItem("Lend book");
            MenuItem lendReadingRoomBookItem = new MenuItem("Lend book for reading room");

            contextMenu.getItems().addAll(archiveItem, lendBookItem, lendReadingRoomBookItem);

            inventoryTableView.setContextMenu(contextMenu);

            archiveItem.setOnAction(this::archiveSelectedBooks);
            lendBookItem.setOnAction(this::lendSelectedBooks);
            lendReadingRoomBookItem.setOnAction(this::lendReadingRoomSelectedBooks);
        } catch (Exception e) {
            logger.error("Error occurred during preparing context menu", e);
        }
    }

    private void updateTableView(List<BookInventory> inventories) {
        try {
            inventoryTableView.getItems().clear();
            inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
        } catch (Exception e) {
            logger.error("Error occurred during updating book table view", e);
        }
    }

    private void archiveSelectedBooks(ActionEvent actionEvent) {
        try {
            Book selectedBook = inventoryTableView.getSelectionModel().getSelectedItem().getBookList().get(0);

            if (selectedBook != null) {
                operatorService.archiveBook(selectedBook);
                updateTableView(operatorService.getAllBookInventories());
                bookTextArea.clear();
            } else {
                bookTextArea.setText("No book selected to archive");
            }
        } catch (Exception e) {
            logger.error("Error occurred during archiving selected books", e);
        }
    }

    @FXML
    private void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            SceneLoader.load(mouseEvent, "/views/base/logInScene.fxml", "LogIn");
        } catch (Exception e) {
            logger.error("Error occurred during logout", e);
        }
    }

    private void lendSelectedBooks(ActionEvent actionEvent) {
        // Implement logic for lending books
    }

    private void lendReadingRoomSelectedBooks(ActionEvent actionEvent) {
        try {
            SceneLoader.load("/views/operator/lendingBookReadingRoomScene.fxml", "Lending book for reading room");
        } catch (Exception e) {
            logger.error("Error occurred during loading lending book for reading room scene", e);
        }
    }
}
