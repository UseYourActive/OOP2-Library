package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
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
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class OperatorReadersController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorReadersController.class);

    @FXML public Button booksButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Reader> readerTableView;
    @FXML public ListView<BookForm> bookFormListView;
    private OperatorService operatorService;
    private TableViewBuilder<Reader> readerTableViewBuilder;
    private SearchEngine<Reader> searchEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);
        searchEngine = new ReaderSearchEngine();

        booksButton.requestFocus();

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        readerTableViewBuilder.updateTableView(readerTableView,operatorService.getAllReaders());

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
            TableView.TableViewSelectionModel<Reader> selectionModel=readerTableView.getSelectionModel();

            if(selectionModel!=null) {
                Reader selectedReader = selectionModel.getSelectedItem();

                if(selectedReader!=null) {
                    bookFormListView.getItems().setAll(selectedReader.getBookForms());
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred during processing reader table view click", e);
        }
    }

    @FXML
    public void bookFormListViewOnMouseClicked() {
        MultipleSelectionModel<BookForm> selectionModel=bookFormListView.getSelectionModel();

        if(selectionModel!=null){
            BookForm selectedBookForm= selectionModel.getSelectedItem();

            String sceneTittle=selectedBookForm.getStatus().getDisplayValue()+selectedBookForm.getDateOfCreation();

            SceneLoader.loadModalityDialog("/views/operator/bookFormShowScene.fxml",sceneTittle,selectedBookForm);
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

                readerTableViewBuilder.updateTableView(readerTableView,operatorService.getAllReaders());
                bookFormListView.getItems().clear();
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected reader", e);
        }
    }

}
