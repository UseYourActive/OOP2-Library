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
import java.util.*;

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
        Set<Book> results = new HashSet<>();
        List<Book> bookList = operatorService.getAllBooks();
        String stringToSearch = searchBarTextField.getText();

        if (stringToSearch.isEmpty()) {
            updateTableView(bookList);
        } else {
            results.addAll(bookList.stream()
                    .filter(book -> book.getTitle().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getAuthor().getName().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getGenre().getValue().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> Objects.nonNull(book.getPublishYear()))
                    .filter(book -> book.getPublishYear().toString().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getResume().contains(stringToSearch))
                    .toList());

            updateTableView(results);
        }
    }

    @FXML
    public void bookTableViewOnClicked(MouseEvent mouseEvent) {

    }

    private void updateTableView(Collection<Book> bookList) {
        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
    }

//    private void checkAndUpdateButtons(MouseEvent mouseEvent) {
//
//        double mouseX = mouseEvent.getSceneX();
//        double mouseY = mouseEvent.getSceneY();
//
//        double textFieldMinX = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMinX();
//        double textFieldMinY = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMinY();
//        double textFieldMaxX = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMaxX();
//        double textFieldMaxY = bookTableView.localToScene(bookTableView.getBoundsInLocal()).getMaxY();
//
//        if (mouseX >= textFieldMinX && mouseX <= textFieldMaxX && mouseY >= textFieldMinY && mouseY <= textFieldMaxY) {
//            if(!bookTableView.getSelectionModel().isEmpty()){
//                removeBookButton.setDisable(false);
//                loadBooksButton.setDisable(false);
//            }
//        }else {
//            removeBookButton.setDisable(true);
//            loadBooksButton.setDisable(true);
//            resumeTextArea.clear();
//            bookTableView.getSelectionModel().clearSelection();
//        }
//
//        anchorPane.requestFocus();
//    }
}
