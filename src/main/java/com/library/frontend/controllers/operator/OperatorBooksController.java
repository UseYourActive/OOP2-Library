package com.library.frontend.controllers.operator;

import com.library.backend.exception.LibraryException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.OperatorBooksService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.BookTreeTableViewBuilder;
import com.library.utils.tableviews.ContextMenuBuilder;
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
 * Controller for managing operator tasks related to books, including searching, archiving, and handling book forms.
 */
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

    private OperatorBooksService service;
    private BookTreeTableViewBuilder bookTreeTableViewBuilder;

    /**
     * Initializes the controller, sets up necessary services, and loads initial data.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(OperatorBooksService.class);

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

    /**
     * Handles the mouse click event on the "Readers" button, navigating to the readers scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Readers");
        }
    }

    /**
     * Handles the mouse click event on the "Search Book" button, searching for books based on user input.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
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

    /**
     * Handles the mouse click event on the "Log Out" button, navigating to the log-in scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "Log In");
        }
    }

    /**
     * Handles the mouse click event on the AnchorPane, clearing the selection in the Book TreeTableView.
     */
    @FXML
    public void anchorPaneOnMouseClicked() {
        bookTreeTableView.getSelectionModel().clearSelection();
    }

    /**
     * Handles the mouse click event on the Book TreeTableView, performing various actions based on the selected item.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
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

    /**
     * Handles the mouse click event on the Selected Books ListView, removing the selected book from the list.
     */
    @FXML
    public void selectedBooksListViewOnMouseClicked() {
        if (selectedBooksListView.getSelectionModel() != null) {
            Book bookToRemove = selectedBooksListView.getSelectionModel().getSelectedItem();
            service.removeFromSelectedBooks(bookToRemove);
        }
    }

    /**
     * Handles the mouse click event on the "Lend" button, initiating the process of lending selected books.
     */
    @FXML
    public void lendButtonOnMouseClicked() {
        if (!service.getSelectedBooks().isEmpty()) {

            Object[] bookArray = service.getSelectedBooks().toArray();
            SceneLoader.loadModalityDialog("/views/operator/createBookFormScene.fxml", "Create Book form", bookArray);
            updateTreeTableView(service.getAllBookInventories());
            selectedBooksListView.getItems().clear();
        }
    }

    /**
     * Handles the mouse click event on the "Inbox" button, displaying overdue book forms in a modal dialog.
     */
    @FXML
    public void inboxButtonOnMouseClicked() {
        Object[] objects = service.getOverdueBookForms().toArray();
        SceneLoader.loadModalityDialog("/views/operator/inboxScene.fxml", "Inbox", objects);
    }

    /**
     * Updates the TreeTableView with the provided list of book inventories.
     *
     * @param bookInventories The list of book inventories to be displayed.
     */
    private void updateTreeTableView(List<BookInventory> bookInventories) {

        //Creating the parents
        bookTreeTableView.getRoot().getChildren().clear();
        for (BookInventory bookInventory : bookInventories) {

            Book parentBook = Book.builder()
                    .id(bookInventory.getRepresentativeBook().getId())
                    .title(bookInventory.getRepresentativeBook().getTitle())
                    .author(bookInventory.getRepresentativeBook().getAuthor())
                    .genre(bookInventory.getRepresentativeBook().getGenre())
                    .publishYear(bookInventory.getRepresentativeBook().getPublishYear())
                    .resume(bookInventory.getRepresentativeBook().getResume())
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

    /**
     * Retrieves the context menu for the BookInventory TreeTableView, including actions like archiving a book.
     *
     * @return The ContextMenu for the TreeTableView.
     */
    private ContextMenu getBookInventoryTreeTableContextMenu(){
        Map<String, EventHandler<ActionEvent>> menuItems=new HashMap<>();

        menuItems.put("Archive book",this::archiveBook);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    /**
     * Archives the selected book from the TreeTableView.
     *
     * @param actionEvent The ActionEvent triggering the archive action.
     */
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
