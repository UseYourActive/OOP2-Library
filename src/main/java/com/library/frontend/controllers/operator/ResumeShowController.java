package com.library.frontend.controllers.operator;

import com.library.frontend.controllers.base.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumeShowController implements Controller {
    @FXML public Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage)(closeButton.getScene().getWindow())).close();
    }
}
