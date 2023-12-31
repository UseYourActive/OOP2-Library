package com.library.frontend.controllers.operator;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.OperatorReadersService;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.ContextMenuBuilder;
import com.library.utils.tableviews.ReaderTableViewBuilder;
import com.library.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OperatorReadersController implements Controller {
    @FXML public Button booksButton;
    @FXML public TextField searchBarTextField;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Reader> readerTableView;
    @FXML public ListView<BookForm> bookFormListView;
    @FXML public Rating readerRatingControl;

    private OperatorReadersService service;
    private TableViewBuilder<Reader> readerTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(OperatorReadersService.class);

        booksButton.requestFocus();

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());

        readerTableView.setContextMenu(getContextMenu());
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
        }
    }

    @FXML
    public void searchReaderButtonOnMouseClicked() {
        try {
            String stringToSearch = searchBarTextField.getText();
            Collection<Reader> results = service.searchReader(stringToSearch);
            readerTableViewBuilder.updateTableView(readerTableView, results);

        } catch (SearchEngineException e) {
            DialogUtils.showInfo("Information", "Reader not found");
        }
    }

    @FXML
    public void readerTableViewOnClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);

            bookFormListView.getItems().setAll(selectedReader.getBookForms());

            service.setRatingValue(selectedReader.getReaderRating().getRating().getValue());
            readerRatingControl.setRating(service.getRatingValue());

            readerRatingControl.setDisable(service.getRatingValue() == -1);

        } catch (NoSuchElementException ignored) {
        }
    }

    @FXML
    public void bookFormListViewOnMouseClicked() {
        MultipleSelectionModel<BookForm> selectionModel = bookFormListView.getSelectionModel();

        if (selectionModel != null) {
            BookForm selectedBookForm = selectionModel.getSelectedItem();

            if (selectedBookForm != null) {
                String sceneTittle = selectedBookForm.getStatus().getDisplayValue() + " " + selectedBookForm.getDateOfCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                SceneLoader.load("/views/operator/bookFormShowScene.fxml", sceneTittle, selectedBookForm);
                readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());
                bookFormListView.getItems().clear();
            }
        }
    }

    @FXML
    public void readerRatingOnMouseClicked() {
        readerRatingControl.setRating(service.getRatingValue());
    }

    private ContextMenu getContextMenu() {
        Map<String, EventHandler<ActionEvent>> menuItems = new HashMap<>();

        menuItems.put("Create Reader", this::createReader);
        menuItems.put("Remove Reader", this::removeReader);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    private void createReader(ActionEvent actionEvent) {
        SceneLoader.load("/views/operator/createReaderProfileScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
    }

    private void removeReader(ActionEvent actionEvent) {

        Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

        if (selectedReader != null) {

            service.removeReader(selectedReader);

            readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());
            bookFormListView.getItems().clear();
        }
    }
}
