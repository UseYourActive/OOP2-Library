package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBooksController implements Controller {
    @FXML public Button addButton;
    @FXML public Button cancelButton;
    @FXML public TextField amountTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addButtonOnMouseClicked(MouseEvent mouseEvent) {
       // ((AdminService)ServiceFactory.getService(AdminService.class)).ge
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml","Administrator");
    }
}
