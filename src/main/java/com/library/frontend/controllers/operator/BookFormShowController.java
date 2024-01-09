package com.library.frontend.controllers.operator;

import com.library.backend.exception.ReturnBookException;
import com.library.backend.exception.email.EmailException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.BookFormShowService;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.HiddenCheckBoxListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for displaying details of a book form in the operator view.
 */
public class BookFormShowController implements Controller {
    @FXML public Button returnButton;
    @FXML public Button closeButton;
    @FXML public Button notifyButton;
    @FXML public CheckListView<Book> bookCheckListView;
    @FXML public Label noteLabel;
    @FXML public Label readerLabel;

    private BookForm bookForm;
    private Reader reader;
    private BookFormShowService service;

    /**
     * Initializes the controller by loading necessary services and setting up the UI components.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(BookFormShowService.class);

        // Load email settings for notifications
        service.loadEmailSettings("ooplibrary7@gmail.com", "ngjh lkzt ehwl urpq");

        // Retrieve necessary objects from the transferable objects
        getTransferObjects();

        // Set up UI components with book form and reader details
        readerLabel.setText(reader.getFullName().replace(' ', '\n'));
        bookCheckListView.getItems().setAll(bookForm.getBooks());

        // Handle UI adjustments based on book form status
        if (bookCheckListView.getItems() == null) {
            DialogUtils.showInfo("Information", "Books were removed from the library");
        }

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

    /**
     * Handles the mouse click event on the "Return" button, allowing the operator to process book returns.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void returnButtonOnMouseClicked(MouseEvent mouseEvent) {
        IndexedCheckModel<Book> checkModel = bookCheckListView.getCheckModel();
        List<Book> damagedBooks = checkModel.getCheckedItems();
        List<Book> allBooks = bookCheckListView.getItems();

        try {
            service.returnBooks(bookForm, damagedBooks, allBooks);
        } catch (ReturnBookException e) {
            DialogUtils.showError("No previous book", e.getMessage());
        }

        closeButtonOnMouseClicked(mouseEvent);
    }

    /**
     * Handles the mouse click event on the "Close" button, navigating back to the operator readers scene.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", SceneLoader.getUser().getUsername() + "(Operator)");
        }
    }

    /**
     * Handles the mouse click event on the "Notify" button, sending an email notification to the reader.
     */
    @FXML
    public void notifyButtonOnMouseClicked() {
        try {
            String message = "You need to return books";
            String subject = "Return of books";

            // Send email notification to the reader
            service.sendEmail(reader, subject, message);

            DialogUtils.showInfo("Email result", "An email notifying the user has been sent!");
        } catch (EmailException e) {
            DialogUtils.showError("Email result", "This email no longer exists!");
        }
    }

    /**
     * Retrieves the necessary transferable objects from the SceneLoader.
     */
    private void getTransferObjects() {
        Object obj = SceneLoader.getTransferableObjects()[0];
        if (obj instanceof BookForm)
            bookForm = (BookForm) obj;
        reader = bookForm.getReader();
    }
}
