package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableViews.TableViewBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Year;
import java.util.*;

public class AdministratorOperatorsController implements Controller{
    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button removeOperatorButton;
    @FXML public Button searchOperatorButton;
    @FXML  public TableView<User> operatorTableView;

    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> booksButton.requestFocus());

        adminService=(AdminService) ServiceFactory.getService(AdminService.class);

        TableViewBuilder.buildOperatorTableView(operatorTableView);//Load columns
        updateTableView(adminService.getUsers()); //populate table
    }

    @FXML
    public void booksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml",SceneLoader.getStage().getTitle());
    }

    @FXML
    public void createOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/createOperatorScene.fxml","Create operator");
    }
    @FXML
    public void searchOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        List<User> userList=adminService.getUsers();
        String stringToSearch=searchBookTextField.getText();

        if(stringToSearch.isEmpty())
        {
            updateTableView(userList);
        }else {
            Set<User> results = new HashSet<>(userList.stream().filter(user -> user.getUsername().contains(stringToSearch)).toList());

            updateTableView(results);
        }
    }
    @FXML
    public void removeOperatorButtonOnMouseClicked() {
        User operator = operatorTableView.getSelectionModel().getSelectedItem();
        adminService.removeOperator(operator);
        operatorTableView.refresh();
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
                removeOperatorButton.setDisable(false);
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
}
