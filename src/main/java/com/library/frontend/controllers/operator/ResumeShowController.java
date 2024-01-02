package com.library.frontend.controllers.operator;

import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumeShowController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ResumeShowController.class);

    @FXML public Button closeButton;
    @FXML public TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String resume = ((String) SceneLoader.getTransferableObjects()[0]);

        textArea.setText(resume);
    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) (closeButton.getScene().getWindow())).close();
    }
}
