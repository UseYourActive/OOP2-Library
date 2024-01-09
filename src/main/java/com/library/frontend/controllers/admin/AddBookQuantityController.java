package com.library.frontend.controllers.admin;

import com.library.backend.exception.InvalidQuantityException;
import com.library.backend.exception.ObjectCannotBeNullException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.AddBookQuantityService;
import com.library.database.entities.BookInventory;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The {@code AddBookQuantityController} class is responsible for managing the user interface interactions related to
 * adding quantity to a book inventory in the library management system. It interacts with the backend
 * {@code AddBookQuantityService} to handle the addition of book quantities.
 *
 * @see Controller
 */
public class AddBookQuantityController implements Controller {

    @FXML public TextField quantityTextField;
    @FXML public Button addButton;
    @FXML public Label informationLabel;

    private AddBookQuantityService service;
    private BookInventory bookInventory;

    /**
     * Initializes the AddBookQuantityController by setting up the required services and retrieving the BookInventory
     * object from the SceneLoader.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(AddBookQuantityService.class);
        bookInventory = (BookInventory) SceneLoader.getTransferableObjects()[0];
    }

    /**
     * Handles the action when the "Add" button is clicked.
     * Attempts to increase the book quantity based on user input and closes the current window on success.
     */
    @FXML
    public void addButtonOnMouseClicked() {
        try {
            service.increaseBookQuantity(quantityTextField.getText(), bookInventory);

            ((Stage) addButton.getScene().getWindow()).close();
        } catch (InvalidQuantityException e) {
            informationLabel.setText("Invalid quantity exception!");
        } catch (NumberFormatException e) {
            informationLabel.setText("Only numbers are allowed!");
        } catch (ObjectCannotBeNullException e) {
            DialogUtils.showError("Object was not found!", e.getMessage());
        }
    }
}
