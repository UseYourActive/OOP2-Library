package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableViews.TableViewBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorOperatorsController implements Controller{
    @FXML public Button booksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button createOperatorButton;
    @FXML public Button removeOperatorButton;
    @FXML public Button searchOperatorButton;
    @FXML  public TableView<User> operatorTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> booksButton.requestFocus());

        populateTableView();
    }

    private void populateTableView(){

        TableViewBuilder.buildOperatorTableView(operatorTableView);

        AdminService service= (AdminService) ServiceFactory.getService(AdminService.class);
        operatorTableView.getItems().addAll(FXCollections.observableArrayList(service.getUsers()));
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
    }
    @FXML
    public void removeOperatorButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
}
