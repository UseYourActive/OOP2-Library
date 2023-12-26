package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class LendingBookReadingRoomController implements Controller {
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public ListView<Book> bookListView;
    @FXML public ListView<Reader> readerListView;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        readerListView.getItems().addAll(operatorService.getAllReaders());
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
    }

    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {

    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operatorBooksScene.fxml", "Operator books scene");
    }
}
