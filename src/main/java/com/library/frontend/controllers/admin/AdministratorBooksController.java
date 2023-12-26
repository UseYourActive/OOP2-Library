package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
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
    @FXML public Button loadBooksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button removeBookButton;

    private AdminService adminService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService=(AdminService) ServiceFactory.getService(AdminService.class);

        operatorsButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder.createInventoryTableViewColumns(inventoryTableView);
        //TableViewBuilder.createBookTableViewColumns(bookTableView);//Load columns
        updateTableView(adminService.getAllBookInventories()); //populate table
    }

    @FXML
    public void registerBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/registerNewBookScene.fxml","Register new book");
    }

    @FXML
    public void loadBooksButtonOnMouseClicked() {
        // Dialog window
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(SceneLoader.getStage());  // Set the owner window


        TextField quantityField = new TextField();
        Button increaseButton = new Button("Increase Quantity");

        increaseButton.setOnAction(e -> {

            int quantity= Integer.parseInt(quantityField.getText());
            if(quantity>0){
                BookInventory bookInventory= inventoryTableView.getSelectionModel().getSelectedItem();
                bookInventory.setQuantity(quantity);
                ((AdminService)ServiceFactory.getService(AdminService.class)).saveInventory(bookInventory);

                //Book book=bookTableView.getSelectionModel().getSelectedItem();
                //((AdminService)ServiceFactory.getService(AdminService.class)).saveBook(book,quantity);
                updateTableView(adminService.getAllBookInventories());
                dialogStage.close();
            }else{
                // ..
            }

        });

        VBox dialogLayout = new VBox(10, new Label("Enter quantity:"), quantityField, increaseButton);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 250, 150);

        dialogStage.setTitle("Quantity Dialog");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);
        Set<BookInventory> results = new HashSet<>();
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


            //results.addAll(bookList.stream()
            //        .filter(book -> book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
            //        .toList());
            //results.addAll(bookList.stream()
            //        .filter(book -> book.getAuthor().getName().toUpperCase().contains(stringToSearch.toUpperCase()))
            //        .toList());
            //results.addAll(bookList.stream()
            //        .filter(book -> book.getGenre().getValue().toUpperCase().contains(stringToSearch.toUpperCase()))
            //        .toList());
            //results.addAll(bookList.stream()
            //        .filter(book -> Objects.nonNull(book.getPublishYear()))
            //        .filter(book -> book.getPublishYear().toString().contains(stringToSearch))
            //        .toList());
            //results.addAll(bookList.stream()
            //        .filter(book -> book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
            //        .toList());
            updateTableView(results);
        }
    }

    //@FXML
    //public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
//
    //    checkAndUpdateButtons(mouseEvent);
//
    //    Set<Book> results=new HashSet<>();
    //    List<Book> bookList=adminService.getAllBooks();
    //    String stringToSearch=searchBookTextField.getText();
//
    //    if(stringToSearch.isEmpty())
    //    {
    //        updateTableView(bookList);
    //    }else {
    //        results.addAll(bookList.stream()
    //                .filter(book -> book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
    //                .toList());
    //        results.addAll(bookList.stream()
    //                .filter(book -> book.getAuthor().getName().toUpperCase().contains(stringToSearch.toUpperCase()))
    //                .toList());
    //        results.addAll(bookList.stream()
    //                .filter(book -> book.getGenre().getValue().toUpperCase().contains(stringToSearch.toUpperCase()))
    //                .toList());
    //        results.addAll(bookList.stream()
    //                .filter(book -> Objects.nonNull(book.getPublishYear()))
    //                .filter(book -> book.getPublishYear().toString().contains(stringToSearch))
    //                .toList());
    //        results.addAll(bookList.stream()
    //                .filter(book -> book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
    //                .toList());
//
    //        updateTableView(results);
    //    }
    //}

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml",SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        //Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        BookInventory bookInventory= inventoryTableView.getSelectionModel().getSelectedItem();


        if(bookInventory!=null)
            bookTextArea.setText(bookInventory.toString());
    }

    @FXML
    public void anchorPaneOnMouseClicked(MouseEvent mouseEvent) {
        anchorPane.requestFocus();
        checkAndUpdateButtons(mouseEvent);
    }

    @FXML
    public void resumeTextAreaOnMouseClicked() {
        removeBookButton.setDisable(true);
        loadBooksButton.setDisable(true);
    }

    @FXML
    public void removeBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        //Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        BookInventory bookInventory= inventoryTableView.getSelectionModel().getSelectedItem();

        if(bookInventory !=null){
            adminService.removeBook(bookInventory);
            updateTableView(adminService.getAllBookInventories());
        }
        checkAndUpdateButtons(mouseEvent);
    }

    private void checkAndUpdateButtons(MouseEvent mouseEvent) {

        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double textFieldMinX = inventoryTableView.localToScene(inventoryTableView.getBoundsInLocal()).getMinX();
        double textFieldMinY = inventoryTableView.localToScene(inventoryTableView.getBoundsInLocal()).getMinY();
        double textFieldMaxX = inventoryTableView.localToScene(inventoryTableView.getBoundsInLocal()).getMaxX();
        double textFieldMaxY = inventoryTableView.localToScene(inventoryTableView.getBoundsInLocal()).getMaxY();

        if (mouseX >= textFieldMinX && mouseX <= textFieldMaxX && mouseY >= textFieldMinY && mouseY <= textFieldMaxY) {
            if(!inventoryTableView.getSelectionModel().isEmpty()){
                removeBookButton.setDisable(false);
                loadBooksButton.setDisable(false);
            }
        }else {
            removeBookButton.setDisable(true);
            loadBooksButton.setDisable(true);
            bookTextArea.clear();
            inventoryTableView.getSelectionModel().clearSelection();
        }

        anchorPane.requestFocus();
    }

    private void updateTableView(Collection<BookInventory> inventories){

        inventoryTableView.getItems().clear();
        bookTextArea.clear();
        inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
    }
}
