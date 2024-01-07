package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.EventNotification;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InboxController implements Controller {

    @FXML public ListView<EventNotification> eventNotificationListView;
    @FXML public Button closeButton;

    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       operatorService= ServiceFactory.getService(OperatorService.class);

       List<EventNotification> eventNotificationList=operatorService.getEventNotifications(SceneLoader.getUser());

       eventNotificationListView.setItems(FXCollections.observableArrayList(eventNotificationList));
    }

    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
