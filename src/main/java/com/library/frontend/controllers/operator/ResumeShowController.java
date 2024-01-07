package com.library.frontend.controllers.operator;

import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumeShowController implements Controller {

    @FXML public Button closeButton;
    @FXML public TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String resume = ((String) SceneLoader.getTransferableObjects()[0]);

        textArea.setText(resume);
    }

    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
