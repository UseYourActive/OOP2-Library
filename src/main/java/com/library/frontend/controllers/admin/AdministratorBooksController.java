package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class AdministratorBooksController implements Controller {
    @FXML public Button operatorsButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService = (AdminService) ServiceFactory.getService(AdminService.class);

        operatorsButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableViewBuilder.createInventoryTableViewColumns(inventoryTableView);

        updateTableView(adminService.getAllBookInventories());

        prepareContextMenu();
    }

    @FXML
    public void searchBookButtonOnMouseClicked() {
        List<BookInventory> results = new ArrayList<>();
        List<BookInventory> inventories = adminService.getAllBookInventories();
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
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/administratorOperatorsScene.fxml", SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
            BookInventory selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                openDialogWithTableView(selectedItem);
            }
        } else {
            BookInventory selectedInventory = inventoryTableView.getSelectionModel().getSelectedItem();

            if (selectedInventory != null)
                bookTextArea.setText(selectedInventory.toString());
        }
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "LogIn");
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        anchorPane.requestFocus();
        inventoryTableView.getSelectionModel().clearSelection();
    }


    private void registerNewBooks(ActionEvent mouseEvent) {
        SceneLoader.load("/views/registerNewBookScene.fxml", "Register new book");
    }

    private void prepareContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem registerNewBook = new MenuItem("Register book/s");
        MenuItem addExistingBookItem = new MenuItem("Add book/s");
        MenuItem removeBookItem = new MenuItem("Remove book/s");


        contextMenu.getItems().addAll(registerNewBook, removeBookItem, addExistingBookItem);

        inventoryTableView.setContextMenu(contextMenu);

        registerNewBook.setOnAction(this::registerNewBooks);
        removeBookItem.setOnAction(this::removeSelectedBooks);
        addExistingBookItem.setOnAction(this::setQuantityOnSelectedBook);
    }

    private void updateTableView(List<BookInventory> inventories) {
        inventoryTableView.getItems().clear();
        bookTextArea.clear();
        inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
    }

    private void removeSelectedBooks(ActionEvent actionEvent) {
        List<BookInventory> inventories = inventoryTableView.getSelectionModel().getSelectedItems();

        if (!inventories.isEmpty()) {
            if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete these book/s from the database ?")) {
                for (BookInventory bookInventory : inventories) {
                    adminService.removeBook(bookInventory);
                    updateTableView(adminService.getAllBookInventories());
                }
            }
        } else {
            DialogUtils.showInfo("Information", "Please select a book!");
        }
    }

    private void setQuantityOnSelectedBook(ActionEvent actionEvent) {

        if (!inventoryTableView.getSelectionModel().isEmpty()) {
            openDialog();
        } else {
            DialogUtils.showInfo("Information", "Please select a book!");
        }
    }

    private void openDialogWithTableView(BookInventory bookInventory) {

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(SceneLoader.getStage());

        TableView<Book> bookTableView = new TableView<>();
        TableViewBuilder.createBookTableViewColumns(bookTableView);
        bookTableView.getItems().addAll(FXCollections.observableArrayList(bookInventory.getBookList()));

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialogStage.close());

        VBox dialogLayout = new VBox(10, new Label(bookInventory.getRepresentiveBook().getTitle()), bookTableView, closeButton);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 500, 600);

        dialogStage.setTitle(bookInventory.getRepresentiveBook().getTitle());
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void openDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(SceneLoader.getStage());


        TextField quantityField = new TextField();
        Button increaseButton = new Button("Increase Quantity");

        increaseButton.setOnAction(e -> {

            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                BookInventory bookInventory = inventoryTableView.getSelectionModel().getSelectedItem();
                //bookInventory.setQuantity(quantity+bookInventory.getQuantity());
                adminService.saveInventory(bookInventory);

                updateTableView(adminService.getAllBookInventories());
                dialogStage.close();
            } else {
                DialogUtils.showInfo("Error", "Please enter valid quantity number!");
            }
        });

        VBox dialogLayout = new VBox(10, new Label("Enter quantity:"), quantityField, increaseButton);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 250, 150);

        dialogStage.setTitle("Set quantity");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

}
