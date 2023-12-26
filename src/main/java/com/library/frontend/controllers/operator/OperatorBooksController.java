package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.GlobalContextMenu;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Collection;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class OperatorBooksController implements Controller {
    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public TextArea bookTextArea;
    @FXML public AnchorPane anchorPane;
    private OperatorService operatorService;

    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        readersButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder.createBookTableViewColumns(bookTableView);
        updateTableView(operatorService.getAllBooks());

        contextMenu = new ContextMenu();

        MenuItem archiveItem = new MenuItem("Archive book");
        MenuItem lendBookItem = new MenuItem("Lend book");
        MenuItem lendReadingRoomBookItem = new MenuItem("Lend book for reading room");

        contextMenu.getItems().addAll(archiveItem, lendBookItem, lendReadingRoomBookItem);

        bookTableView.setContextMenu(contextMenu);

        archiveItem.setOnAction(this::archiveSelectedBooks);
        lendBookItem.setOnAction(this::lendSelectedBooks);
        lendReadingRoomBookItem.setOnAction(this::lendReadingRoomSelectedBooks);
    }

    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operatorReadersScene.fxml", "Readers");
        }
    }

    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        List<Book> results = new ArrayList<>();
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
    public void bookTableViewOnClicked() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null)
            bookTextArea.setText(selectedBook.toString());
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
      //    //anchorPane.requestFocus();
      //    checkAndUpdateButtons(mouseEvent);
    }

    @FXML
    public void bookTextAreaOnMouseClicked() {
    //    archiveButton.setDisable(true);
    //    lendButton.setDisable(true);
    //    lendReadingRoomButton.setDisable(true);
    }


    private void updateTableView(List<Book> bookList) {
          bookTableView.getItems().clear();
          bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
    }

    public void archiveSelectedBooks(ActionEvent actionEvent) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            operatorService.archiveBook(selectedBook);
            updateTableView(operatorService.getAllBooks());
            bookTextArea.clear();
        } else {
            bookTextArea.setText("No book selected to archive");
        }
    }

    public void lendSelectedBooks(ActionEvent actionEvent) {

    }

    public void lendReadingRoomSelectedBooks(ActionEvent actionEvent) {
        SceneLoader.load("/views/lendingBookReadingRoomScene.fxml","Lending book for reading room");
    }
}
