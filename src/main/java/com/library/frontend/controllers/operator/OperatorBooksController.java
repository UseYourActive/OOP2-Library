package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.entities.EventNotification;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.frontend.utils.tableviews.BookTreeTableViewBuilder;
import com.library.frontend.utils.tableviews.ContextMenuBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class OperatorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksController.class);

    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    @FXML public TreeTableView<Book> bookTreeTableView;
    @FXML public ListView<Book> selectedBooksListView;
    @FXML public Button lendButton;
    @FXML public Button inboxButton;

    private OperatorService operatorService;
    private ObservableList<Book> selectedBooks;
    private List<BookForm> overdueBookForms;
    private SearchEngine<BookInventory> searchEngine;
    private BookTreeTableViewBuilder bookTreeTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);
        searchEngine = new BookInventorySearchEngine();

        selectedBooks = FXCollections.observableArrayList();
        selectedBooksListView.setItems(selectedBooks);

        overdueBookForms = operatorService.getAllBookForms().stream().filter(BookForm::isOverdue).toList();

        bookTreeTableViewBuilder = new BookTreeTableViewBuilder();
        bookTreeTableViewBuilder.createTreeTableViewColumns(bookTreeTableView);

        updateTreeTableView(operatorService.getAllBookInventories());

        selectedBooksListView.setTooltip(new Tooltip("Selected books will show here"));

        readersButton.requestFocus();

        for (BookForm bookForm : operatorService.getAllBookForms()) {
            if (bookForm.isPresent() && bookForm.isOverdue()) {
                bookForm.setStatus(BookFormStatus.LATE);
                EventNotification eventNotification= EventNotification.builder()
                        .user(SceneLoader.getUser())
                        .timestamp(LocalDateTime.now())
                        .message("The deadline for returning books of: "+bookForm.getReader().getFullName() + " has passed.")
                        .build();

                operatorService.saveEventNotification(eventNotification);
                operatorService.saveNewBookForm(bookForm);
            }
        }

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
                List<BookInventory> allBookInventories = operatorService.getAllBookInventories();
                Collection<BookInventory> results = searchEngine.search(allBookInventories, stringToSearch);

                updateTreeTableView(results.stream().toList());
            } catch (Exception e) {
                logger.error("Error occurred during searching books", e);
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
            if (bookTreeTableView.getSelectionModel() != null && bookTreeTableView.getSelectionModel().getSelectedItem() != null) {
                if (mouseEvent.getClickCount() == 2
                        && mouseEvent.getButton() == MouseButton.PRIMARY
                        && !bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {

                    SceneLoader.loadModalityDialog("/views/operator/resumeShowScene.fxml", "Resume", bookTreeTableView.getSelectionModel().getSelectedItem().getValue().getResume());

                }

                if (mouseEvent.getButton() == MouseButton.PRIMARY&&bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {

                    Book selectedBook = bookTreeTableView.getSelectionModel().getSelectedItem().getValue();
                    switch (selectedBook.getBookStatus()) {
                        case LENT -> DialogUtils.showInfo("Information", "This book is already given.");
                        case DAMAGED -> DialogUtils.showInfo("Information", "Damaged books are pending removal.");
                        case IN_READING_ROOM ->
                                DialogUtils.showInfo("Information", "This book is currently used by reader.");
                        case ARCHIVED, AVAILABLE -> {
                            if (!selectedBooks.contains(selectedBook))
                                selectedBooks.add(selectedBook);
                        }

                    }

                }

            }
            if(!bookTreeTableViewBuilder.getSelectedItem(bookTreeTableView).isLeaf())
                bookTreeTableView.getSelectionModel().clearSelection();
        } catch (NoSuchElementException ignored) {}
    }

    @FXML
    public void selectedBooksListViewOnMouseClicked() {
        try {
            if (selectedBooksListView.getSelectionModel() != null) {
                selectedBooks.remove(selectedBooksListView.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            logger.error("Error occurred during handling book table view click", e);
        }
    }

    @FXML
    public void lendButtonOnMouseClicked() {
        if (!selectedBooks.isEmpty()) {

            Object[] bookArray = selectedBooks.toArray();
            SceneLoader.loadModalityDialog("/views/operator/createBookFormScene.fxml", "Create Book form", bookArray);
            updateTreeTableView(operatorService.getAllBookInventories());
            selectedBooksListView.getItems().clear();
        }
    }

    @FXML
    public void inboxButtonOnMouseClicked() {
        Object[] objects = overdueBookForms.toArray();
        SceneLoader.loadModalityDialog("/views/operator/inboxScene.fxml", "Inbox", objects);
    }



    private void updateTreeTableView(List<BookInventory> bookInventories) {
        try {
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
        } catch (Exception e) {
            logger.error("Error occurred during updating book tree table view", e);
        }
    }

    private ContextMenu getBookInventoryTreeTableContextMenu(){
        Map<String, EventHandler<ActionEvent>> menuItems=new HashMap<>();

        menuItems.put("Archive book",this::archiveBook);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    private void archiveBook(ActionEvent actionEvent){
        TreeItem<Book> bookTreeItem = bookTreeTableViewBuilder.getSelectedItem(bookTreeTableView);

        Book book = bookTreeItem.getValue();

        book.setBookStatus(BookStatus.ARCHIVED);

        operatorService.saveBook(book);

        updateTreeTableView(operatorService.getAllBookInventories());
        selectedBooksListView.getItems().clear();
    }


}
