package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.engines.ReaderSearchEngine;
import com.library.frontend.utils.engines.SearchEngine;
import com.library.frontend.utils.tableviews.ReaderTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class OperatorReadersController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorReadersController.class);

    @FXML public Button booksButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TextArea readerTextArea;
    @FXML public TableView<Reader> readerTableView;
    private OperatorService operatorService;
    private TableViewBuilder<Reader> readerTableViewBuilder;
    private SearchEngine<Reader> searchEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);
        searchEngine = new ReaderSearchEngine();

        booksButton.requestFocus();

        readerTextArea.setFocusTraversable(false);

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        updateTableView(operatorService.getAllReaders());

        prepareContextMenu();
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", SceneLoader.getUsername() + " (Operator)");
            } catch (Exception e) {
                logger.error("Error occurred during loading operator books scene", e);
            }
        }
    }

    @FXML
    public void searchReaderButtonOnMouseClicked() {
        try {
            List<Reader> readerList = operatorService.getAllReaders();
            String stringToSearch = searchBarTextField.getText();
            Collection<Reader> results = searchEngine.search(readerList, stringToSearch);
            readerTableViewBuilder.updateTableView(readerTableView, results);
        } catch (Exception e) {
            logger.error("Error occurred during searching readers", e);
        }
    }

    @FXML
    public void readerTableViewOnClicked() {
        try {
            Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

            if (selectedReader != null)
                readerTextArea.setText(selectedReader.toString());
        } catch (Exception e) {
            logger.error("Error occurred during processing reader table view click", e);
        }
    }

    private void updateTableView(Collection<Reader> readerList) {
        try {
            readerTableView.getItems().clear();
            readerTableView.getItems().addAll(FXCollections.observableArrayList(readerList));
        } catch (Exception e) {
            logger.error("Error occurred during updating reader table view", e);
        }
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem createReader = new MenuItem("Create Reader");
            MenuItem removeReader = new MenuItem("Remove Reader");

            contextMenu.getItems().addAll(createReader, removeReader);

            readerTableView.setContextMenu(contextMenu);

            createReader.setOnAction(this::createReader);
            removeReader.setOnAction(this::removeReader);
        } catch (Exception e) {
            logger.error("Error occurred during preparing context menu", e);
        }
    }

    private void createReader(ActionEvent actionEvent) {
        try {
            SceneLoader.load("/views/operator/createReaderProfileScene.fxml", SceneLoader.getUsername() + " (Operator)");
        } catch (Exception e) {
            logger.error("Error occurred during loading create reader profile scene", e);
        }
    }

    private void removeReader(ActionEvent actionEvent) {
        try {
            Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

            if (selectedReader != null) {
                operatorService.removeReader(selectedReader);

                updateTableView(operatorService.getAllReaders());
                readerTextArea.clear();
            } else {
                readerTextArea.setText("No reader selected to remove");
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected reader", e);
        }
    }
}
