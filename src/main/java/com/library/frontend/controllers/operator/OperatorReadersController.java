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

/**
 * Controller class for the operator's management of readers.
 * This controller handles operations related to readers, such as searching, displaying details, and managing book forms.
 */
public class OperatorReadersController implements Controller {
    /** Button to navigate to the operator's book management scene. */
    @FXML public Button booksButton;

    /** TextField for entering the search query to find readers. */
    @FXML public TextField searchBarTextField;

    /** Button to initiate the search for readers based on the entered query. */
    @FXML public Button searchReaderButton;

    /** TableView displaying a list of readers. */
    @FXML public TableView<Reader> readerTableView;

    /** ListView displaying book forms associated with the selected reader. */
    @FXML public ListView<BookForm> bookFormListView;

    /** Rating control displaying and allowing interaction with the selected reader's rating. */
    @FXML public Rating readerRatingControl;

    /** Service class providing functionality for operator actions related to readers. */
    private OperatorReadersService service;

    /** TableViewBuilder instance for creating and updating the reader TableView. */
    private TableViewBuilder<Reader> readerTableViewBuilder;

    /**
     * Initializes the OperatorReadersController.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(OperatorReadersService.class);

        booksButton.requestFocus();

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());

        readerTableView.setContextMenu(getContextMenu());
    }

    /**
     * Handles the mouse click event on the "Books" button, navigating to the operator's book management scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorBooksScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
        }
    }

    /**
     * Handles the mouse click event on the "Search Reader" button, initiating the search for readers.
     */
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

    /**
     * Handles the click event on the reader TableView, displaying details and book forms of the selected reader.
     */
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

    /**
     * Handles the mouse click event on the book form ListView, opening a detailed view of the selected book form.
     */
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

    /**
     * Handles the mouse click event on the reader rating control, updating the displayed rating value.
     */
    @FXML
    public void readerRatingOnMouseClicked() {
        readerRatingControl.setRating(service.getRatingValue());
    }

    /**
     * Gets the context menu for the reader TableView, providing additional actions for the operator.
     *
     * @return The ContextMenu for the reader TableView.
     */
    private ContextMenu getContextMenu() {
        Map<String, EventHandler<ActionEvent>> menuItems = new HashMap<>();

        menuItems.put("Create Reader", this::createReader);
        menuItems.put("Remove Reader", this::removeReader);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    /**
     * Event handler for the "Create Reader" context menu item, loading the scene for creating a new reader profile.
     *
     * @param actionEvent The ActionEvent representing the "Create Reader" menu item selection.
     */
    private void createReader(ActionEvent actionEvent) {
        SceneLoader.load("/views/operator/createReaderProfileScene.fxml", SceneLoader.getUser().getUsername() + " (Operator)");
    }

    /**
     * Event handler for the "Remove Reader" context menu item, removing the selected reader from the system.
     *
     * @param actionEvent The ActionEvent representing the "Remove Reader" menu item selection.
     */
    private void removeReader(ActionEvent actionEvent) {

        Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

        if (selectedReader != null) {

            service.removeReader(selectedReader);

            readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());
            bookFormListView.getItems().clear();
        }
    }
}
