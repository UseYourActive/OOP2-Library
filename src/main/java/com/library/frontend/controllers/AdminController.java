package com.library.frontend.controllers;

import com.library.backend.operations.AdminService;
import com.library.backend.operations.OperationFactory;
import com.library.backend.operations.processors.ArchiveBookOperationProcessor;
import com.library.backend.operations.processors.CreateOperatorOperationProcessor;
import com.library.backend.operations.processors.RegisterABookOperationProcessor;
import com.library.backend.operations.processors.RemoveOperatorOperationProcessor;
import com.library.backend.operations.processors.contracts.OperationProcessor;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.frontend.utils.Form;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class AdminController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @FXML
    private Button createOperator;
    @FXML
    private Button removeOperator;
    @FXML
    private Button RegisterABook;
    @FXML
    private Button archiveBook;
    @FXML
    private ListView<User> operatorsListView;
    @FXML
    private ListView<Book> booksListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> createOperator.requestFocus());
    }

    @FXML
    public void createOperatorButtonOnAction(ActionEvent event) {
        Form form = new Form(event,"....","Creating Operator",false);
        form.load();
    }

    @FXML
    public void removeOperatorButtonOnAction(ActionEvent event) {
        AdminService adminService = new AdminService();
        adminService.removeOperator(operatorsListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void registerABookButtonOnAction(ActionEvent event) {
        Form form = new Form(event,"...","Register a book",false);
        form.load();
    }

    @FXML
    public void archiveBookButtonOnAction(ActionEvent event) {
        AdminService adminService = new AdminService();
        adminService.archiveBook(booksListView.getSelectionModel().getSelectedItem());
    }
}
