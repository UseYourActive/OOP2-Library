package com.library.frontend.controllers.operator;

import com.library.backend.exception.ReaderException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.CreateBookFormService;
import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.database.enums.Ratings;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.BookTableViewBuilder;
import com.library.utils.tableviews.ReaderTableViewBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.*;

/**
 * Controller for creating a book form in the operator view.
 */
public class CreateBookFormController implements Controller {
    @FXML public TextField readerSearchBarTextField;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public Button lendReadingRoomButton;
    @FXML public Rating readerRatingControl;
    @FXML public TableView<Reader> readerTableView;

    private CreateBookFormService service;
    private BookTableViewBuilder bookTableViewBuilder;
    private ReaderTableViewBuilder readerTableViewBuilder;

    /**
     * Initializes the controller, loads necessary services, and sets up UI components.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(CreateBookFormService.class);

        // Make the book table view non-interactive
        bookTableView.setMouseTransparent(true);
        bookTableView.setFocusTraversable(false);

        // Retrieve selected books from transferable objects
        List<Book> selectedBooks = new ArrayList<>();
        for (Object object : SceneLoader.getTransferableObjects()) {
            if (object instanceof Book)
                selectedBooks.add((Book) object);
        }

        // Initialize UI components
        readerRatingControl.setRating(Ratings.NONE.getValue());

        bookTableViewBuilder = new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        bookTableViewBuilder.updateTableView(bookTableView, selectedBooks);
        readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());
    }

    /**
     * Handles the mouse click event on the "Search Reader" button, searching for readers based on user input.
     */
    @FXML
    public void searchReaderButtonOnMouseClicked() {
        try {
            String stringToSearch = readerSearchBarTextField.getText();
            Collection<Reader> results = service.searchReader(stringToSearch);
            readerTableViewBuilder.updateTableView(readerTableView, results);
        } catch (Exception e) {
            DialogUtils.showInfo("Information", "Reader not found!");
        }
    }

    /**
     * Handles the mouse click event on the "Lend" button, lending selected books to the chosen reader.
     */
    @FXML
    public void lendButtonOnMouseClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);
            List<Book> bookList = bookTableView.getItems();

            service.lendBooks(selectedReader, bookList);

            // Close the window after lending
            ((Stage) cancelButton.getScene().getWindow()).close();
        } catch (NoSuchElementException ignored) {
        } catch (ReaderException e) {
            DialogUtils.showInfo("Information", e.getMessage());
        }
    }

    /**
     * Handles the mouse click event on the "Lend Reading Room" button, lending selected books for reading in the room.
     */
    @FXML
    public void lendReadingRoomButtonOnMouseClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);
            List<Book> books = bookTableView.getItems();

            service.lendReadingRoomBooks(selectedReader, books);

            // Close the window after lending
            ((Stage) cancelButton.getScene().getWindow()).close();
        } catch (ReaderException e) {
            DialogUtils.showError("Information", e.getMessage());
        } catch (NoSuchElementException ignored) {
        }
    }

    /**
     * Handles the mouse click event on the "Cancel" button, closing the window.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }

    /**
     * Handles the mouse click event on the rating control, updating the rating value.
     */
    @FXML
    public void ratingOnMouseClicked() {
        readerRatingControl.setRating(service.getRatingValue());
    }

    /**
     * Handles the mouse click event on the reader table view, updating the rating control based on the selected reader.
     */
    @FXML
    public void readerTableViewOnMouseClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);

            // Set rating value based on the selected reader
            service.setRatingValue(selectedReader.getReaderRating().getRating().getValue());

            double value = service.getRatingValue();
            readerRatingControl.setRating(value);
            readerRatingControl.setDisable(value == -1);

        } catch (NoSuchElementException ignored) {
        }
    }
}
