package com.library.frontend.controllers.operator;

import com.library.backend.services.ServiceFactory;
import com.library.backend.services.operator.InboxService;
import com.library.database.entities.EventNotification;
import com.library.frontend.SceneLoader;
import com.library.frontend.controllers.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InboxController implements Controller {

    @FXML public ListView<EventNotification> eventNotificationListView;
    @FXML public Button closeButton;

    private InboxService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(InboxService.class);

        List<EventNotification> eventNotificationList = service.getEventNotifications(SceneLoader.getUser());

        eventNotificationListView.setItems(FXCollections.observableArrayList(eventNotificationList));
    }

    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
