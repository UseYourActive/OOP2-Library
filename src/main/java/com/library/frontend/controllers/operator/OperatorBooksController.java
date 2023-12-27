package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class OperatorBooksController implements Controller {
    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public TextArea bookTextArea;
    @FXML public AnchorPane anchorPane;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        readersButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder.createInventoryTableViewColumns(inventoryTableView);
        updateTableView(operatorService.getAllBookInventories());

        prepareContextMenu();
    }

    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operatorReadersScene.fxml", "Readers");
        }
    }

    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            List<BookInventory> results = new ArrayList<>();
            List<BookInventory> inventories = operatorService.getAllBookInventories();
            String stringToSearch = searchBookTextField.getText();
            if (stringToSearch.isEmpty()) {
                updateTableView(inventories);
            } else {

                for(BookInventory inventory:inventories){
                    Book book=inventory.getBook();

                    if(book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
                        results.add(inventory);

                    if(book.getAuthor().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                        results.add(inventory);

                    if(book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
                        results.add(inventory);

                    if(book.getGenre().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                        results.add(inventory);

                    if( book.getPublishYear()!=null && book.getPublishYear().toString().contains(stringToSearch))
                        results.add(inventory);
                }

                updateTableView(results);
            }
        }
    }

    @FXML
    public void bookTableViewOnClicked() {
        BookInventory selectedInventory= inventoryTableView.getSelectionModel().getSelectedItem();

        if (selectedInventory != null)
            bookTextArea.setText(selectedInventory.toString());
    }

    private void prepareContextMenu(){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem archiveItem = new MenuItem("Archive book");
        MenuItem lendBookItem = new MenuItem("Lend book");
        MenuItem lendReadingRoomBookItem = new MenuItem("Lend book for reading room");

        contextMenu.getItems().addAll(archiveItem, lendBookItem, lendReadingRoomBookItem);

        inventoryTableView.setContextMenu(contextMenu);

        archiveItem.setOnAction(this::archiveSelectedBooks);
        lendBookItem.setOnAction(this::lendSelectedBooks);
        lendReadingRoomBookItem.setOnAction(this::lendReadingRoomSelectedBooks);
    }

    private void updateTableView(List<BookInventory> inventories) {
        inventoryTableView.getItems().clear();
        inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
    }

    private void archiveSelectedBooks(ActionEvent actionEvent) {
        Book selectedBook = inventoryTableView.getSelectionModel().getSelectedItem().getBook();

        if (selectedBook != null) {
            operatorService.archiveBook(selectedBook);
            updateTableView(operatorService.getAllBookInventories());
            bookTextArea.clear();
        } else {
            bookTextArea.setText("No book selected to archive");
        }
    }

    private void lendSelectedBooks(ActionEvent actionEvent) {

    }

    private void lendReadingRoomSelectedBooks(ActionEvent actionEvent) {
        SceneLoader.load("/views/lendingBookReadingRoomScene.fxml", "Lending book for reading room");
    }
}
