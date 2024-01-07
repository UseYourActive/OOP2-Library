package com.library.frontend.controllers.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.admin.OperatorControllerService;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.ContextMenuBuilder;
import com.library.frontend.utils.tableviews.OperatorTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class AdministratorOperatorsController implements Controller {

    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchOperatorButton;
    @FXML public TableView<User> operatorTableView;
    @FXML public AnchorPane anchorPane;

    private OperatorControllerService service;
    private TableViewBuilder<User> operatorTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service=new OperatorControllerService(UserRepository.getInstance());

        booksButton.requestFocus();

        operatorTableViewBuilder = new OperatorTableViewBuilder();
        operatorTableViewBuilder.createTableViewColumns(operatorTableView);

        operatorTableViewBuilder.updateTableView(operatorTableView, service.getAllUsers());

        operatorTableView.setContextMenu(getContextMenu());
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
    public void searchOperatorButtonOnMouseClicked() {
        try {
            String stringToSearch = searchBookTextField.getText();
            Collection<User> results = service.searchUser(stringToSearch);
            operatorTableViewBuilder.updateTableView(operatorTableView,results.stream().toList());
            anchorPane.requestFocus();
        }catch (SearchEngineException e){
            DialogUtils.showInfo("Error ","User not found!");
        }
    }

    private ContextMenu getContextMenu() {
        Map<String, EventHandler<ActionEvent>> menuItems= new HashMap<>();

        menuItems.put("Create operator",this::createOperator);
        menuItems.put("Remove operator",this::removeOperator);

        return ContextMenuBuilder.prepareContextMenu(menuItems);
    }

    private void createOperator(ActionEvent actionEvent){
        SceneLoader.load("/views/admin/createOperatorScene.fxml", "Create operator");
    }

    private void removeOperator(ActionEvent actionEvent){
        try{
            User user = operatorTableViewBuilder.getSelectedItem(operatorTableView);

            service.removeOperator(user);

            operatorTableViewBuilder.updateTableView(operatorTableView,service.getAllUsers());
        }catch (Exception e){
            DialogUtils.showInfo("Error", e.getMessage());
        }
    }
}
