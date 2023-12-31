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

public class AddBookQuantityController implements Controller {
    @FXML public TextField quantityTextField;
    @FXML public Button addButton;
    @FXML public Label informationLabel;

    private AddBookQuantityService service;
    private BookInventory bookInventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(AddBookQuantityService.class);
        bookInventory = (BookInventory) SceneLoader.getTransferableObjects()[0];
    }

    @FXML
    public void addButtonOnMouseClicked() {
        try {
            service.increaseBookQuantity(quantityTextField.getText(), bookInventory);
        } catch (InvalidQuantityException e) {
            DialogUtils.showError("Invalid quantity exception!", e.getMessage());
        } catch (NumberFormatException e) {
            DialogUtils.showError("Only numbers are allowed!", e.getMessage());
        } catch (ObjectCannotBeNullException e) {
            DialogUtils.showError("Object was not found!", e.getMessage());
        }
        ((Stage) addButton.getScene().getWindow()).close();
    }
}
