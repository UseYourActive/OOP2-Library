package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class OperatorBooksController implements Controller {
    @FXML public Button readersButton;
    @FXML public Button archiveButton;
    @FXML public Button lendButton;
    @FXML public Button lendReadingRoomButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchBookButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public TextArea bookTextArea;

    @Setter
    private String stageTitle;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        bookTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = (newValue != null);
            archiveButton.setDisable(!isItemSelected);
            lendButton.setDisable(!isItemSelected);
            lendReadingRoomButton.setDisable(!isItemSelected);

            if (isItemSelected) {
                bookTextArea.setText(newValue.toString());
            } else {
                bookTextArea.clear();
            }
        });

        TableViewBuilder.buildBookTableView(bookTableView);
        updateTableView(operatorService.getAllBooks());
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

    }

    @FXML
    public void bookTableViewOnClicked(MouseEvent mouseEvent) {

    }

    private void updateTableView(Collection<Book> bookList) {
        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
    }
}
