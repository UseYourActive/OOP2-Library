package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.database.enums.ReaderRating;
import com.library.frontend.controllers.Controller;
import com.library.frontend.controllers.admin.AdministratorBooksController;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.ReaderTableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class CreateBookFormController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public TextField readerSearchBarTextField;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public Button lendReadingRoomButton;
    @FXML public Rating rating;
    @FXML public TableView<Reader> readerTableView;

    private OperatorService operatorService;
    private double selectedReaderRating;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        rating.setRating(ReaderRating.NONE.getValue());

        BookTableViewBuilder bookTableViewBuilder=new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        ReaderTableViewBuilder readerTableViewBuilder=new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

    }
    @FXML
    public void searchReaderButtonOnMouseClicked() {
        try {
            Set<Reader> results = new HashSet<>();
            List<Reader> readerList = operatorService.getAllReaders();
            String stringToSearch = searchReaderButton.getText();

            if (stringToSearch.isEmpty()) {
                updateTableView(readerTableView,readerList);
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
                        .filter(reader -> reader.getEmail().toUpperCase().contains(stringToSearch.toUpperCase()))
                        .toList());

                results.addAll(readerList.stream()
                        .filter(reader -> reader.getPhoneNumber().contains(stringToSearch.toUpperCase()))
                        .toList());

                updateTableView(readerTableView,results);
            }
        } catch (Exception e) {
            logger.error("Error occurred during searching readers", e);
        }
    }
    @FXML
    public void lendButtonOnMouseClicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", "Operator books scene");
    }
    @FXML
    public void lendReadingRoomButtonOnMouseClicked() {

    }
    @FXML
    public void ratingOnMouseClicked() {
        rating.setRating(selectedReaderRating);
    }
    @FXML
    public void readerTableViewOnMouseClicked() {
        if(readerTableView.getSelectionModel()!=null&&readerTableView.getSelectionModel().getSelectedItem()!=null){
            selectedReaderRating=readerTableView.getSelectionModel().getSelectedItem().getRating().getValue();
            rating.setRating(selectedReaderRating);
        }
    }

    private <T>void updateTableView(TableView<T> tableView, Collection<T> inventories) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(FXCollections.observableArrayList(inventories));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }
}
