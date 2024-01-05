package com.library.frontend.controllers.admin;

import com.library.backend.engines.OperatorSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.trying.AdminOperatorService;
import com.library.backend.services.trying.ContextMenuService;
import com.library.database.entities.User;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.OperatorTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorOperatorsController.class);

    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchOperatorButton;
    @FXML public TableView<User> operatorTableView;
    @FXML public AnchorPane anchorPane;

    private AdminOperatorService adminOperatorService;
    private SearchEngine<User> searchEngine;
    private TableViewBuilder<User> operatorTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminOperatorService = new AdminOperatorService();
        searchEngine = new OperatorSearchEngine();

        booksButton.requestFocus();

        operatorTableViewBuilder = new OperatorTableViewBuilder();
        operatorTableViewBuilder.createTableViewColumns(operatorTableView);

        operatorTableViewBuilder.updateTableView(operatorTableView, adminOperatorService.getAllOperators());

        prepareContextMenu();
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/admin/administratorBooksScene.fxml", SceneLoader.getUser().getUsername() + "(Administrator)");
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        anchorPane.requestFocus();
        operatorTableView.getSelectionModel().clearSelection();
    }

    @FXML
    public void operatorTableViewOnMouseClicked() {
        // Handle mouse click on table view if needed
    }

    @FXML
    public void searchOperatorButtonOnMouseClicked() {
        try {
            List<User> userList = adminOperatorService.getAllOperators();
            String stringToSearch = searchBookTextField.getText();
            Collection<User> results = searchEngine.search(userList, stringToSearch);
            operatorTableViewBuilder.updateTableView(operatorTableView, results);
            anchorPane.requestFocus();
        } catch (Exception e) {
            logger.error("Error occurred during operator search", e);
        }
    }

    private void prepareContextMenu() {
        ContextMenu contextMenu = ContextMenuService.createOperatorContextMenu(adminOperatorService, operatorTableViewBuilder, operatorTableView);
        operatorTableView.setContextMenu(contextMenu);
    }
}
