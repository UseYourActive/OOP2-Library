package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.GlobalContextMenu;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class OperatorBooksController implements Controller {
    @FXML public Button readersButton;
    @FXML public Button archiveButton;
    @FXML public Button lendButton;
    @FXML public Button lendReadingRoomButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public TextArea bookTextArea;
    @FXML public AnchorPane anchorPane;
    private OperatorService operatorService;
    private GlobalContextMenu globalContextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        readersButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder.createBookTableViewColumns(bookTableView);
        updateTableView(operatorService.getAllBooks());

        globalContextMenu = new GlobalContextMenu();

        globalContextMenu.addAction("Archive selected book", this::archiveSelectedBook);
        globalContextMenu.addAction("Lend selected book", this::lendSelectedBook);
        globalContextMenu.addAction("Lend for reading room", this::lendForReadingRoom);

        globalContextMenu.attachToNode(bookTableView);
    }

    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operatorReadersScene.fxml", "Readers");
    }

    @FXML
    public void archiveButtonOnMouseClicked(MouseEvent mouseEvent) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            operatorService.archiveBook(selectedBook);

            updateTableView(operatorService.getAllBooks());
            bookTextArea.clear();
        } else {
            bookTextArea.setText("No book selected to archive");
        }
    }

    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void lendReadingRoomButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/lendingBookReadingRoomScene.fxml", "Lending book for reading room");
    }

    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        Set<Book> results = new HashSet<>();
        List<Book> bookList = operatorService.getAllBooks();
        String stringToSearch = searchBookTextField.getText();

        if (stringToSearch.isEmpty()) {
            updateTableView(bookList);
        } else {
            results.addAll(bookList.stream()
                    .filter(book -> book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getAuthor().getName().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getGenre().getValue().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> Objects.nonNull(book.getPublishYear()))
                    .filter(book -> book.getPublishYear().toString().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());

            updateTableView(results);
        }
    }

    @FXML
    public void bookTableViewOnClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null)
            bookTextArea.setText(selectedBook.toString());
    }

    @FXML
    public void anchorPaneOnMouseClicked(MouseEvent mouseEvent) {
        anchorPane.requestFocus();
        checkAndUpdateButtons(mouseEvent);
    }

    @FXML
    public void bookTextAreaOnMouseClicked() {
        archiveButton.setDisable(true);
        lendButton.setDisable(true);
        lendReadingRoomButton.setDisable(true);
    }

    private void updateTableView(Collection<Book> bookList) {
        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
    }

    private void checkAndUpdateButtons(MouseEvent mouseEvent) {

        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double textFieldMinX = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMinX();
        double textFieldMinY = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMinY();
        double textFieldMaxX = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMaxX();
        double textFieldMaxY = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMaxY();

        if (mouseX >= textFieldMinX && mouseX <= textFieldMaxX && mouseY >= textFieldMinY && mouseY <= textFieldMaxY) {
            if (!bookTableView.getSelectionModel().isEmpty()) {
                archiveButton.setDisable(false);
                lendButton.setDisable(false);
                lendReadingRoomButton.setDisable(false);
            }
        } else {
            archiveButton.setDisable(true);
            lendButton.setDisable(true);
            lendReadingRoomButton.setDisable(true);
            bookTextArea.clear();
            bookTableView.getSelectionModel().clearSelection();
        }

        anchorPane.requestFocus();
    }

    public void archiveSelectedBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            operatorService.archiveBook(selectedBook);
            updateTableView(operatorService.getAllBooks());
            bookTextArea.clear();
        } else {
            bookTextArea.setText("No book selected to archive");
        }
    }

    public void lendSelectedBook() {

    }

    public void lendForReadingRoom() {
        SceneLoader.load(anchorPane.getScene(), "/views/lendingBookReadingRoomScene.fxml", "Lending book for reading room");
    }
}
