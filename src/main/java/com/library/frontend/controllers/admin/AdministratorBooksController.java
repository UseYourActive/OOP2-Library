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
    @FXML public Button registerBookButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;

    private AdminService adminService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService=(AdminService) ServiceFactory.getService(AdminService.class);

        operatorsButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableViewBuilder.createInventoryTableViewColumns(inventoryTableView);

        updateTableView(adminService.getAllBookInventories()); //populate table

        prepareContextMenu();
    }


    public void searchBookButtonOnMouseClicked() {
        List<BookInventory> results = new ArrayList<>();
        List<BookInventory> inventories = adminService.getAllBookInventories();
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

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml",SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void booksTableViewOnClicked() {
        BookInventory selectedInventory= inventoryTableView.getSelectionModel().getSelectedItem();

        if(selectedInventory!=null)
            bookTextArea.setText(selectedInventory.toString());

    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        anchorPane.requestFocus();
        inventoryTableView.getSelectionModel().clearSelection();
    }

    private void prepareContextMenu(){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem removeBookItem = new MenuItem("Remove book/s");
        MenuItem addExistingBookItem = new MenuItem("Add book/s");

        contextMenu.getItems().addAll(removeBookItem, addExistingBookItem);

        inventoryTableView.setContextMenu(contextMenu);

        removeBookItem.setOnAction(this::removeSelectedBooks);
        addExistingBookItem.setOnAction(this::setQuantityOnSelectedBook);
    }

    private void updateTableView(List<BookInventory> inventories){
        inventoryTableView.getItems().clear();
        bookTextArea.clear();
        inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
    }

    private void removeSelectedBooks(ActionEvent actionEvent){
        List<BookInventory> inventories= inventoryTableView.getSelectionModel().getSelectedItems();

        if(!inventories.isEmpty()){
            if(DialogUtils.showConfirmation("Confirmation","Are you sure you want to delete these book/s")){
                for(BookInventory bookInventory:inventories){
                    adminService.removeBook(bookInventory);
                    updateTableView(adminService.getAllBookInventories());
                }
            }
        }else {
            DialogUtils.showInfo("Information","Please select a book!");
        }
    }

    private void setQuantityOnSelectedBook(ActionEvent actionEvent) {

        if(!inventoryTableView.getSelectionModel().isEmpty()) {

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(SceneLoader.getStage());  // Set the owner window


            TextField quantityField = new TextField();
            Button increaseButton = new Button("Increase Quantity");

            //Button logic
            increaseButton.setOnAction(e -> {

                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    BookInventory bookInventory = inventoryTableView.getSelectionModel().getSelectedItem();
                    bookInventory.setQuantity(quantity);
                    ((AdminService) ServiceFactory.getService(AdminService.class)).saveInventory(bookInventory);

                    updateTableView(adminService.getAllBookInventories());
                    dialogStage.close();
                } else {
                    DialogUtils.showInfo("Error","Please enter valid quantity number!");
                }
            });

            VBox dialogLayout = new VBox(10, new Label("Enter quantity:"), quantityField, increaseButton);
            dialogLayout.setPadding(new Insets(20));

            Scene dialogScene = new Scene(dialogLayout, 250, 150);

            dialogStage.setTitle("Set quantity");
            dialogStage.setScene(dialogScene);
            dialogStage.show();
        }else{
            DialogUtils.showInfo("Information","Please select a book!");
        }
    }

    public void registerBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/registerNewBookScene.fxml","Register new book");
    }
}
