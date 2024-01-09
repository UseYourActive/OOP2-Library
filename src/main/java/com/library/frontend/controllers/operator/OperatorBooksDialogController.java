package com.library.frontend.controllers.operator;

import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.tableviews.BookTableViewBuilder;
import com.library.utils.tableviews.TableViewBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the operator's dialog displaying books associated with a specific book inventory.
 * This dialog allows the operator to view and manage books within the selected book inventory.
 */
public class OperatorBooksDialogController implements Controller {

    /** Button to close the dialog. */
    @FXML private Button closeButton;

    /** TableView displaying the books associated with the selected book inventory. */
    @FXML private TableView<Book> bookTableView;

    /** TableViewBuilder instance for creating and updating the book TableView. */
    private TableViewBuilder<Book> tableViewBuilder;

    /**
     * Initializes the OperatorBooksDialogController.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the selection mode to allow multiple book selection in the TableView
        bookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create a BookTableViewBuilder instance and initialize columns for the book TableView
        tableViewBuilder = new BookTableViewBuilder();
        tableViewBuilder.createTableViewColumns(bookTableView);

        // Retrieve the selected BookInventory from the SceneLoader transferable objects
        BookInventory bookInventory = (BookInventory) SceneLoader.getTransferableObjects()[0];

        // Get the list of books associated with the selected BookInventory
        List<Book> bookList = bookInventory.getBookList();

        // Update the TableView with the list of books
        tableViewBuilder.updateTableView(bookTableView, bookList);
    }

    /**
     * Handles the mouse click event on the close button, closing the dialog.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    private void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // Close the dialog window
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
