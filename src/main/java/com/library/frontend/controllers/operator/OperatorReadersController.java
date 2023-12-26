package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class OperatorReadersController implements Controller {
    @FXML public Button booksButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TextArea readerTextArea;
    @FXML public TableView<Reader> readerTableView;
    private OperatorService operatorService;
    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        booksButton.requestFocus();

        readerTextArea.setFocusTraversable(false);

        TableViewBuilder.createReaderTableViewColumns(readerTableView);
        updateTableView(operatorService.getAllReaders());

        contextMenu = new ContextMenu();

        MenuItem createReader = new MenuItem("Create Reader");
        MenuItem removeReader = new MenuItem("Remove Reader");

        contextMenu.getItems().addAll(createReader, removeReader);

        readerTableView.setContextMenu(contextMenu);

        createReader.setOnAction(this::createReader);
        removeReader.setOnAction(this::removeReader);
    }
    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/operatorBooksScene.fxml", SceneLoader.getUsername() +" (Operator)");
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

    @FXML
    public void readerTableViewOnClicked(MouseEvent mouseEvent) {

    }

    private void updateTableView(Collection<Reader> readerList) {
        readerTableView.getItems().clear();
        readerTableView.getItems().addAll(FXCollections.observableArrayList(readerList));
    }

    public void createReader(ActionEvent actionEvent){
        SceneLoader.load("/views/createReaderProfileScene.fxml", SceneLoader.getUsername() +" (Operator)");
    }

    public void removeReader(ActionEvent actionEvent){
        Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

        if (selectedReader != null) {
            operatorService.removeReader(selectedReader);

            updateTableView(operatorService.getAllReaders());
            readerTextArea.clear();
        } else {
            readerTextArea.setText("No reader selected to remove");
        }
    }
}
