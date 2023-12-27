package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.OperatorTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class AdministratorOperatorsController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorOperatorsController.class);

    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button removeOperatorButton;
    @FXML public Button searchOperatorButton;
    @FXML public TableView<User> operatorTableView;
    @FXML public AnchorPane anchorPane;
    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService = (AdminService) ServiceFactory.getService(AdminService.class);

        booksButton.requestFocus();

        TableViewBuilder<User> operatorTableViewBuilder = new OperatorTableViewBuilder();
        operatorTableViewBuilder.createTableViewColumns(operatorTableView);

        updateTableView(adminService.getAllUsers());

        prepareContextMenu();
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/administratorBooksScene.fxml", SceneLoader.getUsername() + "(Administrator)");
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
            Set<User> results = new HashSet<>();
            List<User> userList = adminService.getAllUsers();
            String stringToSearch = searchBookTextField.getText();

            if (stringToSearch.isEmpty()) {
                updateTableView(userList);
            } else {
                results.addAll(userList.stream()
                        .filter(user -> user.getUsername().toUpperCase().contains(stringToSearch.toUpperCase()))
                        .toList());
                results.addAll(userList.stream()
                        .filter(user -> user.getRole().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                        .toList());

                updateTableView(results);
            }
            anchorPane.requestFocus();
        } catch (Exception e) {
            logger.error("Error occurred during operator search", e);
        }
    }

    private void prepareContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem removeOperator = new MenuItem("Remove");
        MenuItem createOperator = new MenuItem("Create operator");

        contextMenu.getItems().addAll(createOperator, removeOperator);

        operatorTableView.setContextMenu(contextMenu);

        removeOperator.setOnAction(this::removeSelectedOperator);
        createOperator.setOnAction(this::createOperator);
    }

    private void createOperator(ActionEvent actionEvent) {
        try {
            SceneLoader.load("/views/createOperatorScene.fxml", "Create operator");
        } catch (Exception e) {
            logger.error("Error occurred during operator creation", e);
        }
    }

    private void removeSelectedOperator(ActionEvent mouseEvent) {
        try {
            User operator = operatorTableView.getSelectionModel().getSelectedItem();

            if (operator != null) {
                adminService.removeOperator(operator);
                updateTableView(adminService.getAllUsers());
            }
        } catch (Exception e) {
            logger.error("Error occurred during operator removal", e);
        }
    }

    private void updateTableView(Collection<User> userList) {
        operatorTableView.getItems().clear();
        operatorTableView.getItems().addAll(FXCollections.observableArrayList(userList));
    }
}
