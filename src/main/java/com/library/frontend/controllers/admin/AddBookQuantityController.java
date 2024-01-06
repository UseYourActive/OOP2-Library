package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookQuantityController implements Controller {
    @FXML public TextField quantityTextField;
    @FXML public Button addButton;
    @FXML public Label informationLabel;

    private AdminService adminService;
    private BookInventory bookInventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService= ServiceFactory.getService(AdminService.class);
        bookInventory= (BookInventory) SceneLoader.getTransferableObjects()[0];
    }
    @FXML
    public void addButtonOnMouseClicked() {
        try {
            int quantity = Integer.parseInt(quantityTextField.getText());

            if (quantity<0)
                throw new NumberFormatException();

            Book representiveBook = bookInventory.getRepresentiveBook();

            for (int i = 0; i < quantity; i++) {
                Book book = new Book(representiveBook);
                book.setBookStatus(BookStatus.AVAILABLE);
                book.setNumberOfTimesUsed(0);

                adminService.saveBook(book);
                bookInventory.addBook(book);
            }

            adminService.saveInventory(bookInventory);

        }catch (NumberFormatException e){
            informationLabel.setText("Incorrect input");
        }
    }
}
