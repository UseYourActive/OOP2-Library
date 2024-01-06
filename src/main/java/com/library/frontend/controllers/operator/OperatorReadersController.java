package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.BookForm;
import com.library.database.entities.EventNotification;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.frontend.utils.tableviews.ReaderTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class OperatorReadersController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorReadersController.class);

    @FXML public Button booksButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Reader> readerTableView;
    @FXML public ListView<BookForm> bookFormListView;
    public Rating readerRating;

    private int ratingValue;
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

        readerTableViewBuilder.updateTableView(readerTableView, operatorService.getAllReaders());

        prepareContextMenu();
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
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
            TableView.TableViewSelectionModel<Reader> selectionModel = readerTableView.getSelectionModel();

            if (selectionModel != null) {
                Reader selectedReader = selectionModel.getSelectedItem();

                if (selectedReader != null) {
                    bookFormListView.getItems().setAll(selectedReader.getBookForms());
                    ratingValue=selectedReader.getRating().getValue();
                    readerRating.setRating(ratingValue);

                    readerRating.setDisable(ratingValue == -1);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred during processing reader table view click", e);
        }
    }

    @FXML
    public void bookFormListViewOnMouseClicked() {
        MultipleSelectionModel<BookForm> selectionModel = bookFormListView.getSelectionModel();

        if (selectionModel != null) {
            BookForm selectedBookForm = selectionModel.getSelectedItem();

            if(selectedBookForm!=null) {
                String sceneTittle = selectedBookForm.getStatus().getDisplayValue() + " " + selectedBookForm.getDateOfCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                SceneLoader.load("/views/operator/bookFormShowScene.fxml", sceneTittle, selectedBookForm);
                readerTableViewBuilder.updateTableView(readerTableView, operatorService.getAllReaders());
                bookFormListView.getItems().clear();
            }
        }
    }

    @FXML
    public void readerRatingOnMouseClicked() {
        readerRating.setRating(ratingValue);
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
            SceneLoader.load("/views/operator/createReaderProfileScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
        } catch (Exception e) {
            logger.error("Error occurred during loading create reader profile scene", e);
        }
    }

    private void removeReader(ActionEvent actionEvent) {
        try {
            Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

            if (selectedReader != null) {

                operatorService.removeReader(selectedReader);

                readerTableViewBuilder.updateTableView(readerTableView, operatorService.getAllReaders());
                bookFormListView.getItems().clear();
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected reader", e);
        }
    }


}
