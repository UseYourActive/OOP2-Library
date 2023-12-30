package com.library.frontend.controllers;

import javafx.fxml.Initializable;

/**
 * The {@code Controller} interface serves as the base interface for all JavaFX controllers
 * in the library frontend. It extends the {@link javafx.fxml.Initializable} interface, which
 * ensures that implementing classes provide an initialization method for their controllers.
 * This interface acts as a marker interface to identify classes responsible for controlling
 * the behavior and interaction of corresponding JavaFX views.
 * <p>
 * JavaFX's controllers are responsible for initializing the UI components, handling user
 * input, and managing the communication between the view and the underlying data model.
 * Implementing classes should override the {@link javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)}
 * method to perform any necessary setup when the controller is initialized.
 *
 * @see javafx.fxml.Initializable
 */
public interface Controller extends Initializable {
    // This interface currently does not declare additional methods.
    // It serves as a marker interface for JavaFX controllers.
}

