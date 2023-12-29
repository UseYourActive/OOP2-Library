package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTreeTableViewBuilder;
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
    @FXML public TextArea bookTextArea;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    @FXML public TreeTableView<Book> bookTreeTableView;

    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        bookTreeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        BookTreeTableViewBuilder bookTreeTableViewBuilder=new BookTreeTableViewBuilder();
        bookTreeTableViewBuilder.createTreeTableViewColumns(bookTreeTableView);

        populateTreeTableView();


        readersButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        //updateTableView(operatorService.getAllBookInventories());

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
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/logInScene.fxml","Log In");
    }
    @FXML
    public void anchorPaneOnMouseClicked(MouseEvent mouseEvent) {
        bookTreeTableView.getSelectionModel().clearSelection();
    }

    @FXML
    public void bookTreeTableViewOnMouseClicked(MouseEvent mouseEvent) {
        if(!bookTreeTableView.getSelectionModel().isEmpty()&&bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {
            bookTextArea.setText(bookTreeTableView.getSelectionModel().getSelectedItem().getValue().toString());

            for (TreeItem<Book> bookTreeItem : bookTreeTableView.getSelectionModel().getSelectedItems()) {
                if (!bookTreeItem.isLeaf()) {
                    bookTreeTableView.getSelectionModel().clearSelection();
                    break;
                }
            }
        }
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem archiveItem = new MenuItem("Archive book");
            MenuItem lendBookItem = new MenuItem("Lend book");
            MenuItem lendReadingRoomBookItem = new MenuItem("Lend book for reading room");

            contextMenu.getItems().addAll(archiveItem, lendBookItem, lendReadingRoomBookItem);

            bookTreeTableView.setContextMenu(contextMenu);

            archiveItem.setOnAction(this::archiveSelectedBooks);
            lendBookItem.setOnAction(this::lendSelectedBooks);
            lendReadingRoomBookItem.setOnAction(this::lendReadingRoomSelectedBooks);
        } catch (Exception e) {
            logger.error("Error occurred during preparing context menu", e);
        }
    }

    private void updateTableView(List<BookInventory> inventories) {
        try {
            //bookTreeTableView.getRoot().getChildren().clear();
            //populateTreeTableView();
            //bookTreeTableView.getItems().clear();
            //bookTreeTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
        } catch (Exception e) {
            logger.error("Error occurred during updating book table view", e);
        }
    }

    private void archiveSelectedBooks(ActionEvent actionEvent) {
      //try {
      //    Book selectedBook = bookTreeTableView.getSelectionModel().getSelectedItem().getBookList().get(0);

      //    if (selectedBook != null) {
      //        operatorService.archiveBook(selectedBook);
      //        updateTableView(operatorService.getAllBookInventories());
      //        bookTextArea.clear();
      //    } else {
      //        bookTextArea.setText("No book selected to archive");
      //    }
      //} catch (Exception e) {
      //    logger.error("Error occurred during archiving selected books", e);
      //}
    }

    private void lendSelectedBooks(ActionEvent actionEvent) {
        // Implement logic for lending books
    }

    private void lendReadingRoomSelectedBooks(ActionEvent actionEvent) {
        //try {
        //    SceneLoader.load("/views/lendingBookReadingRoomScene.fxml", "Lending book for reading room");
        //} catch (Exception e) {
        //    logger.error("Error occurred during loading lending book for reading room scene", e);
        //}
    }



    private void populateTreeTableView(){
        //Creating the parents
        for(BookInventory bookInventory: operatorService.getAllBookInventories()){

            Book parentBook=Book.builder()
                    .title(bookInventory.getRepresentiveBook().getTitle()+" "+bookInventory.getRepresentiveBook().getAuthor())
                    .build();

            TreeItem<Book> parent =new TreeItem<>(parentBook);

            //Creating the children
            for(Book book:bookInventory.getBookList()){

                TreeItem<Book> child=new TreeItem<>(book);

                parent.getChildren().add(child);//Adding child to the root element
            }

            bookTreeTableView.getRoot().getChildren().add(parent);
        }

    }
}
