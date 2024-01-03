package com.library.frontend.controllers.operator;

import com.library.backend.exception.email.EmailException;
import com.library.backend.exception.email.TransportException;
import com.library.backend.services.EmailSenderService;
import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.tableviews.HiddenCheckBoxListCell;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML public Label noteLabel;
    @FXML public Label readerLabel;

    private BookForm bookForm;
    private Reader reader;
    private OperatorService operatorService;
    private EmailSenderService emailSenderService;

    // Database updates overtime ... Best to execute when the app is started
    static {
        OperatorService service = ServiceFactory.getService(OperatorService.class);

        for (BookForm bookForm : service.getAllBookForms()) {
            if (bookForm.isPresent() && bookForm.isOverdue())
                bookForm.setStatus(BookFormStatus.LATE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing BookFormShowController");
        operatorService = ServiceFactory.getService(OperatorService.class);

        try {
            emailSenderService = new EmailSenderService("ooplibrary7@gmail.com", "ngjh lkzt ehwl urpq", "smtp.gmail.com", "587", false);
        } catch (TransportException e) {
            logger.error("Couldn't send email", e);
        }

        Object obj = SceneLoader.getTransferableObjects()[0];
        if (obj instanceof BookForm)
            bookForm = (BookForm) obj;

        reader = bookForm.getReader();

        readerLabel.setText(reader.getFullName().replace(' ', '\n'));

        bookCheckListView.getItems().setAll(bookForm.getBooks());

        if (!bookForm.isPresent()) {
            returnButton.setVisible(false);
            returnButton.setDisable(true);
            notifyButton.setVisible(false);
            notifyButton.setDisable(true);
            noteLabel.setVisible(false);
            noteLabel.setDisable(true);
            bookCheckListView.setCellFactory(param -> new HiddenCheckBoxListCell<>());
        }
    }

    @FXML
    public void returnButtonOnMouseClicked() {
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
            reader.demote();
        } else {
            reader.promote();
        }

        bookForm.setStatus(BookFormStatus.RETURNED);

        operatorService.saveReader(reader);

        logger.info("Books returned successfully by reader: {}", reader.getFullName());

        closeButtonOnMouseClicked();
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        logger.info("BookFormShow window closed.");
    }

    @FXML
    public void notifyButtonOnMouseClicked() {
        try {
            emailSenderService.sendEmail(reader.getEmail(), "Return of books", "");
            logger.info("Notification sent to reader: {}", reader.getEmail());
            DialogUtils.showConfirmation("Email result", "An email notifying the user has been sent!");
        } catch (EmailException e) {
            logger.error("Error sending notification email", e);
        }
    }

}
