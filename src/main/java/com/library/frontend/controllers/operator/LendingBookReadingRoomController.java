package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class LendingBookReadingRoomController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(LendingBookReadingRoomController.class);
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public ListView<Book> bookListView;
    @FXML public ListView<Reader> readerListView;private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        readerListView.getItems().addAll(operatorService.getAllReaders());

        bookListView.getItems().addAll(operatorService.getAllBooks().stream()
                .filter(book -> book.getBookStatus() == BookStatus.AVAILABLE)
                .toList());
    }

    @FXML
    public void searchReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
        List<Reader> results = new ArrayList<>();
        List<Reader> readerList = operatorService.getAllReaders();
        String stringToSearch = searchReaderButton.getText();

        if (stringToSearch.isEmpty()) {
            updateTableView(readerList);
        } else {
            results.addAll(readerList.stream()
                    .filter(reader -> reader.getFirstName().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(readerList.stream()
                    .filter(reader -> reader.getMiddleName().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(readerList.stream()
                    .filter(reader -> reader.getLastName().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(readerList.stream()
                    .filter(reader -> reader.getPhoneNumber().contains(stringToSearch))
                    .toList());
            results.addAll(readerList.stream()
                    .filter(reader -> reader.getEmail().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());

            updateTableView(results);
        }
    }

    private void updateTableView(Collection<Reader> readerList) {
        readerListView.getItems().clear();
        readerListView.getItems().addAll(FXCollections.observableArrayList(readerList));
        logger.info("Updated reader list view.");
    }

    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        Reader selectedReader = readerListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null && selectedReader != null) {
            operatorService.lendBookToReader(selectedBook, selectedReader);
            updateTableView(operatorService.getAllReaders());
            updateAvailableBooksListView();
            logger.info("Book successfully lent to reader: {} - {}", selectedReader.getFirstName() + " " + selectedReader.getMiddleName() + " " + selectedReader.getLastName(), selectedBook.getTitle());
        } else {
            logger.warn("Please select a book and a reader to lend.");
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operatorBooksScene.fxml", "Operator books scene");
    }

    private void updateAvailableBooksListView() {
        bookListView.getItems().clear();
        bookListView.getItems().addAll(
                operatorService.getAllBooks().stream()
                        .filter(book -> book.getBookStatus() == BookStatus.AVAILABLE)
                        .toList());
        logger.info("Updated available books list view.");
    }
}