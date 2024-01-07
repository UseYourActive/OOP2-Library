package com.library.frontend.controllers.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.ServiceFactory;
import com.library.backend.services.admin.AdministratorOperatorsService;
import com.library.database.entities.User;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import com.library.utils.DialogUtils;
import com.library.utils.tableviews.ContextMenuBuilder;
import com.library.utils.tableviews.OperatorTableViewBuilder;
import com.library.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller {

    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchOperatorButton;
    @FXML public TableView<User> operatorTableView;
    @FXML public AnchorPane anchorPane;

    private AdministratorOperatorsService service;
    private TableViewBuilder<User> operatorTableViewBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(AdministratorOperatorsService.class);

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
