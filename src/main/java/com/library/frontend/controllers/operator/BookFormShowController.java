package com.library.frontend.controllers.operator;

import com.library.backend.exception.email.EmailException;
import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.HiddenCheckBoxListCell;
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

public class BookFormShowController implements Controller {

    @FXML public Button returnButton;
    @FXML public Button closeButton;
    @FXML public Button notifyButton;
    @FXML public CheckListView<Book> bookCheckListView;
    @FXML public Label noteLabel;
    @FXML public Label readerLabel;

    private BookForm bookForm;
    private Reader reader;
    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        operatorService = ServiceFactory.getService(OperatorService.class);

        operatorService.loadEmailSettings("ooplibrary7@gmail.com", "ngjh lkzt ehwl urpq");

        getTransferObjects();

        readerLabel.setText(reader.getFullName().replace(' ', '\n'));

        bookCheckListView.getItems().setAll(bookForm.getBooks());

        if(bookCheckListView.getItems()==null){
            DialogUtils.showInfo("Information","Books were removed from the library");
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

        if(bookCheckListView.getItems().isEmpty()){
            DialogUtils.showInfo("Information","Books were removed from this ");
        }
    }

    @FXML
    public void returnButtonOnMouseClicked(MouseEvent mouseEvent) {
        IndexedCheckModel<Book> checkModel = bookCheckListView.getCheckModel();
        List<Book> damagedBooks= checkModel.getCheckedItems();
        List<Book> allBooks= bookCheckListView.getItems();

        operatorService.returnBooks(bookForm,damagedBooks,allBooks);

        closeButtonOnMouseClicked(mouseEvent);
    }

    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", SceneLoader.getUser().getUsername() + "(Operator)");
        }
    }

    @FXML
    public void notifyButtonOnMouseClicked() {
        try {
            String message = "You need to return books";
            String subject = "Return of books";

            operatorService.sendEmail(reader,subject,message);

            DialogUtils.showInfo("Email result", "An email notifying the user has been sent!");
        } catch (EmailException e) {
            DialogUtils.showError("Email result", "This email no longer exists!");
        }
    }

    private void getTransferObjects(){
        Object obj = SceneLoader.getTransferableObjects()[0];
        if (obj instanceof BookForm)
            bookForm = (BookForm) obj;
        reader = bookForm.getReader();
    }

}
