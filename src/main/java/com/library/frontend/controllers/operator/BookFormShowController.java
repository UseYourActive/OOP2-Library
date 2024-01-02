package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookFormShowController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(BookFormShowController.class);

    @FXML public Button returnButton;
    @FXML public Button closeButton;
    @FXML public Button notifyButton;
    @FXML public CheckListView<Book> bookCheckListView;
    @FXML public Label readerLabel;

    private BookForm bookForm;
    private Reader reader;
    private OperatorService operatorService;

    // Database updates overtime .. Best to execute when the app is started
    static {
        OperatorService service = ServiceFactory.getService(OperatorService.class);

        for (BookForm bookForm : service.getAllBookForms()) {
            if (bookForm.isPresent() && bookForm.isOverdue())
                bookForm.setStatus(BookFormStatus.LATE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = ServiceFactory.getService(OperatorService.class);

        Object obj = SceneLoader.getTransferableObjects()[0];
        if (obj instanceof BookForm)
            bookForm = (BookForm) obj;

        reader = bookForm.getReader();

        if (!bookForm.isPresent()) {
            returnButton.setVisible(false);
            returnButton.setDisable(true);
            notifyButton.setVisible(false);
            notifyButton.setDisable(true);
        }

        readerLabel.setText(reader.getFullName().replace(' ', '\n'));

        bookCheckListView.getItems().setAll(bookForm.getBooks());
    }

    @FXML
    public void returnButtonOnMouseClicked(MouseEvent mouseEvent) {
        IndexedCheckModel<Book> checkModel = bookCheckListView.getCheckModel();

        List<Book> booksToReturn = new ArrayList<>();

        for (Book book : bookCheckListView.getItems()) {
            if (checkModel.getCheckedItems().contains(book))
                book.setBookStatus(BookStatus.DAMAGED);
            else
                book.setBookStatus(BookStatus.AVAILABLE);

            booksToReturn.add(book);
        }

        operatorService.saveAllBooks(booksToReturn);

        if (bookForm.isOverdue()) {
            reader.getRating().demote();
        } else {
            reader.getRating().promote();
        }

        bookForm.setStatus(BookFormStatus.RETURNED);

        operatorService.saveReader(reader);

        logger.info("Books returned successfully by reader: {}", reader.getFullName());
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) closeButton.getScene().getWindow()).close();
        logger.info("BookFormShow window closed.");
    }

    @FXML
    public void notifyButtonOnMouseClicked(MouseEvent mouseEvent) {
        // sending email to reader bookForm.getReader()
        logger.info("Notification sent to reader: {}", reader.getEmail());
    }

    @FXML
    public void bookCheckListViewOnMouseClicked(MouseEvent mouseEvent) {
        // Any specific behavior for bookCheckListView click event
    }
}
