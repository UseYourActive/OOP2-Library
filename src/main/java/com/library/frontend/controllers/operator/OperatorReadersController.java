package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class OperatorReadersController implements Controller {
    @FXML public Button booksButton;
    @FXML public Button createReaderButton;
    @FXML public Button removeReaderButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TextArea readerTextArea;
    @FXML public TableView<Reader> readerTableView;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);
    }
    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/operatorBooksScene.fxml", SceneLoader.getUsername() +" (Operator)");
    }
    @FXML
    public void createReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/createReaderProfileScene.fxml", SceneLoader.getUsername() +" (Operator)");
    }
    @FXML
    public void removeReaderButtonOnMouseClicked(MouseEvent mouseEvent) {
        Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

        if (selectedReader != null) {
            operatorService.removeReader(selectedReader);

            updateTableView(operatorService.getAllReaders());
            readerTextArea.clear();
        } else {
            readerTextArea.setText("No reader selected to remove");
        }
    }
    @FXML
    public void searchReaderButtonOnMouseClicked(MouseEvent mouseEvent) {

    }

    public void readerTableViewOnClicked(MouseEvent mouseEvent) {

    }

    private void updateTableView(Collection<Reader> readerList) {
        readerTableView.getItems().clear();
        readerTableView.getItems().addAll(FXCollections.observableArrayList(readerList));
    }
}
