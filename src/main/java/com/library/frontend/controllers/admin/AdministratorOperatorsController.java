package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class AdministratorOperatorsController implements Controller {
    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button removeOperatorButton;
    @FXML public Button searchOperatorButton;
    @FXML  public TableView<User> operatorTableView;
    @FXML public AnchorPane anchorPane;

    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        booksButton.requestFocus();

        adminService=(AdminService) ServiceFactory.getService(AdminService.class);

        TableViewBuilder.createOperatorTableViewColumns(operatorTableView);//Load columns
        updateTableView(adminService.getAllUsers()); //populate table
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml",SceneLoader.getUsername() + "(Administrator)");
    }

    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/createOperatorScene.fxml","Create operator");
    }
    @FXML
    public void searchOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);
        Set<User> results=new HashSet<>();
        List<User> userList=adminService.getAllUsers();
        String stringToSearch=searchBookTextField.getText();

        if(stringToSearch.isEmpty())
        {
            updateTableView(userList);
        }else {
            results.addAll(userList.stream()
                    .filter(user -> user.getUsername().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());
            results.addAll(userList.stream()
                    .filter(user-> user.getRole().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .toList());

            updateTableView(results);
        }
        anchorPane.requestFocus();
    }
    @FXML
    public void removeOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        User operator = operatorTableView.getSelectionModel().getSelectedItem();

        if(operator !=null){
            adminService.removeOperator(operator);
            updateTableView(adminService.getAllUsers());
        }

        checkAndUpdateButtons(mouseEvent);
    }

    private void checkAndUpdateButtons(MouseEvent mouseEvent) {

        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double textFieldMinX = operatorTableView.localToScene(operatorTableView.getBoundsInLocal()).getMinX();
        double textFieldMinY = operatorTableView.localToScene(operatorTableView.getBoundsInLocal()).getMinY();
        double textFieldMaxX = operatorTableView.localToScene(operatorTableView.getBoundsInLocal()).getMaxX();
        double textFieldMaxY = operatorTableView.localToScene(operatorTableView.getBoundsInLocal()).getMaxY();

        if (mouseX >= textFieldMinX && mouseX <= textFieldMaxX && mouseY >= textFieldMinY && mouseY <= textFieldMaxY) {
            if(!operatorTableView.getSelectionModel().isEmpty()){
                removeOperatorButton.setDisable(!operatorTableView.getSelectionModel().getSelectedItem().getRole().equals(Role.OPERATOR));
            }
        }else {
            removeOperatorButton.setDisable(true);

            operatorTableView.getSelectionModel().clearSelection();
        }
    }

    private void updateTableView(Collection<User> userList){
        operatorTableView.getItems().clear();
        operatorTableView.getItems().addAll(FXCollections.observableArrayList(userList));
    }

    @FXML
    public void anchorPaneOnMouseClicked(MouseEvent mouseEvent) {
        anchorPane.requestFocus();
        checkAndUpdateButtons(mouseEvent);
    }

    @FXML
    public void operatorTableViewOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        //User selectedUser = operatorTableView.getSelectionModel().getSelectedItem();
//
        //if(selectedUser!=null&&selectedUser.getRole()== Role.OPERATOR)
        //    removeOperatorButton.setDisable(true);
    }
}
