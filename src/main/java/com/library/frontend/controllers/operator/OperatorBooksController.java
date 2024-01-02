package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.engines.BookInventorySearchEngine;
import com.library.frontend.utils.engines.SearchEngine;
import com.library.frontend.utils.tableviews.BookTreeTableViewBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);
        searchEngine = new BookInventorySearchEngine();

        selectedBooks = FXCollections.observableArrayList();
        selectedBooksListView.setItems(selectedBooks);

        overdueBookForms = operatorService.getAllBookForms().stream().filter(BookForm::isOverdue).toList();

        BookTreeTableViewBuilder bookTreeTableViewBuilder = new BookTreeTableViewBuilder();
        bookTreeTableViewBuilder.createTreeTableViewColumns(bookTreeTableView);

        updateTreeTableView(operatorService.getAllBookInventories());

        selectedBooksListView.setTooltip(new Tooltip("Selected books will show here"));

        readersButton.requestFocus();

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
        SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "Log In");
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

                if (bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {

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
                bookTreeTableView.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            logger.error("Error occurred during handling book table view click", e);
        }
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

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem resume = new MenuItem("Show resume");

            contextMenu.getItems().add(resume);

            bookTreeTableView.setContextMenu(contextMenu);

            resume.setOnAction(this::showResume);

        } catch (Exception e) {
            logger.error("Error occurred during context menu preparation", e);
        }
    }

    private void showResume(ActionEvent actionEvent) {
        SceneLoader.loadModalityDialog("/views/operator/resumeShowScene.fxml", "Resume");
    }


    private void updateTreeTableView(List<BookInventory> bookInventories) {
        try {
            //Creating the parents
            bookTreeTableView.getRoot().getChildren().clear();
            for (BookInventory bookInventory : bookInventories) {

                Book parentBook = Book.builder()
                        .id(bookInventory.getRepresentiveBook().getId())
                        .title(bookInventory.getRepresentiveBook().getTitle() + " " + bookInventory.getRepresentiveBook().getAuthor())
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


}
