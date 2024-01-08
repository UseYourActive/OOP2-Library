package com.library.frontend.controllers.operator;

import com.library.backend.exception.LibraryException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.OperatorBooksControllerService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.frontend.SceneLoader;
import com.library.utils.tableviews.BookTreeTableViewBuilder;
import com.library.utils.tableviews.ContextMenuBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.boot.archive.spi.ArchiveException;

import java.net.URL;
import java.util.*;

public class OperatorBooksController implements Controller {

    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    @FXML public TreeTableView<Book> bookTreeTableView;
    @FXML public ListView<Book> selectedBooksListView;
    @FXML public Button lendButton;
    @FXML public Button inboxButton;

    private OperatorBooksControllerService service;
    private BookTreeTableViewBuilder bookTreeTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(OperatorBooksControllerService.class);

        service.initializeSelectedBooks();
        selectedBooksListView.setItems(service.getSelectedBooks());

        service.setAllOverdueBooks();

        bookTreeTableViewBuilder = new BookTreeTableViewBuilder();
        bookTreeTableViewBuilder.createTreeTableViewColumns(bookTreeTableView);

        updateTreeTableView(service.getAllBookInventories());

        selectedBooksListView.setTooltip(new Tooltip("Selected books will show here"));

        readersButton.requestFocus();

        service.updateBookForms();

        bookTreeTableView.setContextMenu(getBookInventoryTreeTableContextMenu());
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
                String stringToSearch = searchBookTextField.getText().toUpperCase();
                Collection<BookInventory> results = service.searchBookInventory(stringToSearch);
                updateTreeTableView(results.stream().toList());
            } catch (SearchEngineException e) {
                DialogUtils.showInfo("Information","Book not found");
            }
        }
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "Log In");
        }
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        bookTreeTableView.getSelectionModel().clearSelection();
    }

    @FXML
    public void bookTreeTableViewOnMouseClicked(MouseEvent mouseEvent) {
        try {

            TreeItem<Book> selectedTreeItem = bookTreeTableViewBuilder.getSelectedItem(bookTreeTableView);
            Book selectedBook = selectedTreeItem.getValue();

            if (mouseEvent.getClickCount() == 2
                    && mouseEvent.getButton() == MouseButton.PRIMARY
                    && !selectedTreeItem.isLeaf()) {

                SceneLoader.loadModalityDialog("/views/operator/resumeShowScene.fxml", "Resume", selectedBook.getResume());

            }

            if (mouseEvent.getButton() == MouseButton.PRIMARY && selectedTreeItem.isLeaf()) {


                switch (selectedBook.getBookStatus()) {
                    case LENT -> DialogUtils.showInfo("Information", "This book is already given.");
                    case DAMAGED -> DialogUtils.showInfo("Information", "Damaged books are pending removal.");
                    case IN_READING_ROOM ->
                            DialogUtils.showInfo("Information", "This book is currently used by reader.");
                    case ARCHIVED, AVAILABLE ->
                            service.addSelectedBookToList(selectedBook);

                }

            }

            if(!selectedTreeItem.isLeaf())
                bookTreeTableView.getSelectionModel().clearSelection();
        } catch (NoSuchElementException ignored) {}
    }

    @FXML
    public void selectedBooksListViewOnMouseClicked() {
        if (selectedBooksListView.getSelectionModel() != null) {
            Book bookToRemove = selectedBooksListView.getSelectionModel().getSelectedItem();
            service.removeFromSelectedBooks(bookToRemove);
        }
    }

    @FXML
    public void lendButtonOnMouseClicked() {
        if (!service.getSelectedBooks().isEmpty()) {

            Object[] bookArray = service.getSelectedBooks().toArray();
            SceneLoader.loadModalityDialog("/views/operator/createBookFormScene.fxml", "Create Book form", bookArray);
            updateTreeTableView(service.getAllBookInventories());
            selectedBooksListView.getItems().clear();
        }
    }

    @FXML
    public void inboxButtonOnMouseClicked() {
        Object[] objects = service.getOverdueBookForms().toArray();
        SceneLoader.loadModalityDialog("/views/operator/inboxScene.fxml", "Inbox", objects);
    }



    private void updateTreeTableView(List<BookInventory> bookInventories) {

        //Creating the parents
        bookTreeTableView.getRoot().getChildren().clear();
        for (BookInventory bookInventory : bookInventories) {

            Book parentBook = Book.builder()
                    .id(bookInventory.getRepresentiveBook().getId())
                    .title(bookInventory.getRepresentiveBook().getTitle())
                    .author(bookInventory.getRepresentiveBook().getAuthor())
                    .genre(bookInventory.getRepresentiveBook().getGenre())
                    .publishYear(bookInventory.getRepresentiveBook().getPublishYear())
                    .resume(bookInventory.getRepresentiveBook().getResume())
                    .build();

            TreeItem<Book> parent = new TreeItem<>(parentBook);

            //Creating the children
            for (Book book : bookInventory.getBookList()) {

                TreeItem<Book> child = new TreeItem<>(book);

                parent.getChildren().add(child);//Adding child to the root element
            }

            bookTreeTableView.getRoot().getChildren().add(parent);
        }
    }

    private ContextMenu getBookInventoryTreeTableContextMenu(){
        Map<String, EventHandler<ActionEvent>> menuItems=new HashMap<>();

        menuItems.put("Archive book",this::archiveBook);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    private void archiveBook(ActionEvent actionEvent){
        try {
            TreeItem<Book> bookTreeItem = bookTreeTableViewBuilder.getSelectedItem(bookTreeTableView);

            Book book = bookTreeItem.getValue();

            service.archiveBook(book);

            updateTreeTableView(service.getAllBookInventories());
            selectedBooksListView.getItems().clear();

        }catch (NoSuchElementException ignored){}
        catch (LibraryException e){
            DialogUtils.showInfo("Information",e.getMessage());
        }
    }


}
