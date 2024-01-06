package com.library.frontend.controllers.operator;

import com.library.backend.exception.ReaderException;
import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.database.enums.ReaderRating;
import com.library.frontend.controllers.Controller;
import com.library.frontend.controllers.admin.AdministratorBooksController;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.ReaderTableViewBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class CreateBookFormController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public TextField readerSearchBarTextField;
    @FXML public Button lendButton;
    @FXML public Button cancelButton;
    @FXML public Button searchReaderButton;
    @FXML public TableView<Book> bookTableView;
    @FXML public Button lendReadingRoomButton;
    @FXML public Rating readerRating;
    @FXML public TableView<Reader> readerTableView;

    private OperatorService operatorService;
    private double ratingValue;
    private BookTableViewBuilder bookTableViewBuilder;
    private ReaderTableViewBuilder readerTableViewBuilder;
    private SearchEngine<Reader> searchEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);
        searchEngine = new ReaderSearchEngine();

        bookTableView.setMouseTransparent(true);
        bookTableView.setFocusTraversable(false);


        List<Book> selectedBooks = new ArrayList<>();
        for (Object object : SceneLoader.getTransferableObjects()) {
            if (object instanceof Book)
                selectedBooks.add((Book) object);
        }

        readerRating.setRating(ReaderRating.NONE.getValue());

        bookTableViewBuilder = new BookTableViewBuilder();
        bookTableViewBuilder.createTableViewColumns(bookTableView);

        readerTableViewBuilder = new ReaderTableViewBuilder();
        readerTableViewBuilder.createTableViewColumns(readerTableView);

        bookTableViewBuilder.updateTableView(bookTableView, selectedBooks);

        readerTableViewBuilder.updateTableView(readerTableView, operatorService.getAllReaders());
    }

    @FXML
    public void searchReaderButtonOnMouseClicked() {
        try {
            List<Reader> readerList = operatorService.getAllReaders();
            String stringToSearch = readerSearchBarTextField.getText();
            Collection<Reader> results = searchEngine.search(readerList, stringToSearch);
            readerTableViewBuilder.updateTableView(readerTableView, results);
        } catch (Exception e) {
            logger.error("Error occurred during searching readers", e);
        }
    }

    @FXML
    public void lendButtonOnMouseClicked() throws ReaderException {
        if (readerTableView.getSelectionModel() != null && readerTableView.getSelectionModel().getSelectedItem() != null) {

            Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

            if (selectedReader.getRating() == ReaderRating.ZERO_STAR)
                throw new ReaderException("The reader is not allowed to take books anymore.");

            if (bookTableView.getItems().stream()
                    .allMatch(book -> book.getBookStatus()
                            .equals(BookStatus.AVAILABLE))) {

                operatorService.changeBookStatus(bookTableView.getItems(), BookStatus.LENT);

                BookForm bookForm = BookForm.builder()
                        .reader(selectedReader)
                        .books(bookTableView.getItems())
                        .status(BookFormStatus.IN_USE)
                        .expirationDate(LocalDateTime.now().plusMonths(1))
                        .dateOfCreation(LocalDateTime.now())
                        .build();

                operatorService.saveNewBookForm(bookForm);

                selectedReader.getBookForms().add(bookForm);

                operatorService.saveReader(selectedReader);

                ((Stage) cancelButton.getScene().getWindow()).close();
            } else {
                DialogUtils.showInfo("Information", "For normal lending reader\ncan take only AVAILABLE books.");
            }
        }
    }

    @FXML
    public void lendReadingRoomButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (readerTableView.getSelectionModel() != null && readerTableView.getSelectionModel().getSelectedItem() != null) {
            Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

            if (selectedReader.getRating() == ReaderRating.ZERO_STAR){
                DialogUtils.showError("Reader is not allowed to take books anymore","His rating is too low.");
            }
            else {

                operatorService.changeBookStatus(bookTableView.getItems(), BookStatus.IN_READING_ROOM);

                BookForm bookForm = BookForm.builder()
                        .reader(selectedReader)
                        .books(bookTableView.getItems())
                        .status(BookFormStatus.IN_USE)
                        .expirationDate(LocalDateTime.now().plusHours(12))
                        .dateOfCreation(LocalDateTime.now())
                        .build();

                operatorService.saveNewBookForm(bookForm);

                selectedReader.getBookForms().add(bookForm);

                operatorService.saveReader(selectedReader);

                ((Stage) cancelButton.getScene().getWindow()).close();
            }
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    public void ratingOnMouseClicked() {
        readerRating.setRating(ratingValue);
    }

    @FXML
    public void readerTableViewOnMouseClicked() {
        TableView.TableViewSelectionModel<Reader> selectionModel = readerTableView.getSelectionModel();

        if (selectionModel != null) {
            Reader selectedReader = selectionModel.getSelectedItem();

            if (selectedReader != null) {
                ratingValue=selectedReader.getRating().getValue();
                readerRating.setRating(ratingValue);

                readerRating.setDisable(ratingValue == -1);
            }
        }
    }
}
