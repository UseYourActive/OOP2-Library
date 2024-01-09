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

/**
 * Controller for managing the operator's inbox, displaying event notifications.
 */
public class InboxController implements Controller {

    @FXML public ListView<EventNotification> eventNotificationListView;
    @FXML public Button closeButton;

    private InboxService service;

    /**
     * Initializes the controller and loads the necessary services.
     *
     * @param location  The URL location.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFactory.getService(InboxService.class);

        // Retrieve event notifications for the current user
        List<EventNotification> eventNotificationList = service.getEventNotifications(SceneLoader.getUser());

        // Set the retrieved event notifications to the ListView
        eventNotificationListView.setItems(FXCollections.observableArrayList(eventNotificationList));
    }

    /**
     * Handles the mouse click event on the "Close" button, closing the inbox window.
     *
     * @param mouseEvent The MouseEvent representing the mouse click event.
     */
    @FXML
    public void closeButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // Close the inbox window
            ((Stage) closeButton.getScene().getWindow()).close();
        }
    }
}
