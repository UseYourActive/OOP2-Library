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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(CreateBookFormService.class);

        bookTableView.setMouseTransparent(true);
        bookTableView.setFocusTraversable(false);


        List<Book> selectedBooks = new ArrayList<>();
        for (Object object : SceneLoader.getTransferableObjects()) {
            if (object instanceof Book)
                selectedBooks.add((Book) object);
        }

        readerRatingControl.setRating(Ratings.NONE.getValue());

        bookTableViewBuilder = new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        bookTableViewBuilder.updateTableView(bookTableView, selectedBooks);

        readerTableViewBuilder.updateTableView(readerTableView, service.getAllReaders());
    }

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

    @FXML
    public void lendButtonOnMouseClicked() {

        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);
            List<Book> bookList = bookTableView.getItems();

            service.lendBooks(selectedReader, bookList);

            ((Stage) cancelButton.getScene().getWindow()).close();
        } catch (NoSuchElementException ignored) {
        } catch (ReaderException e) {
            DialogUtils.showInfo("Information", e.getMessage());
        }
    }

    @FXML
    public void lendReadingRoomButtonOnMouseClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);
            List<Book> books = bookTableView.getItems();

            service.lendReadingRoomBooks(selectedReader, books);
            ((Stage) cancelButton.getScene().getWindow()).close();
        } catch (ReaderException e) {
            DialogUtils.showError("Information", e.getMessage());
        } catch (NoSuchElementException ignored) {
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }

    @FXML
    public void ratingOnMouseClicked() {
        readerRatingControl.setRating(service.getRatingValue());
    }

    @FXML
    public void readerTableViewOnMouseClicked() {
        try {
            Reader selectedReader = readerTableViewBuilder.getSelectedItem(readerTableView);

            service.setRatingValue(selectedReader.getReaderRating().getRating().getValue());

            double value = service.getRatingValue();
            readerRatingControl.setRating(value);
            readerRatingControl.setDisable(value == -1);

        } catch (NoSuchElementException ignored) {
        }
    }
}
