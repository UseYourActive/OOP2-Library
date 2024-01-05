package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.BookForm;
import com.library.database.entities.EventNotification;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InboxController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(InboxController.class);

    @FXML public ListView<EventNotification> eventNotificationListView;
    @FXML public Button closeButton;

    private OperatorService operatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       operatorService= ServiceFactory.getService(OperatorService.class);

       List<EventNotification> eventNotificationList=operatorService.getAllEventNotifications().stream().filter(event -> event.getUser().equals(SceneLoader.getUser())).toList();

       eventNotificationListView.setItems(FXCollections.observableArrayList(eventNotificationList));
    }

    @FXML
    public void bookFormListViewOnMouseClicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void closeButtonOnMouseClicked() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
}
