package com.library.frontend.controllers.operator;

import com.library.database.entities.BookForm;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InboxController implements Controller {

    @FXML public ListView<BookForm> bookFormListView;
    @FXML public Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Object object: SceneLoader.getTransferableObjects()){
            if(object instanceof BookForm)
                bookFormListView.getItems().add((BookForm) object);
        }
    }

    @FXML
    public void bookFormListViewOnMouseClicked(MouseEvent mouseEvent) {

    }
    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage)closeButton.getScene().getWindow()).close();
    }
}
