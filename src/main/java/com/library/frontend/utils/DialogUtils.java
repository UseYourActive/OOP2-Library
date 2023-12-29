package com.library.frontend.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The {@code DialogUtils} class provides utility methods for displaying various types
 * of JavaFX dialogs, including alerts and confirmation dialogs.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Show an information alert
 * DialogUtils.showInfo("Information", "This is an informative message.");
 *
 * // Show an error alert
 * DialogUtils.showError("Error", "An error occurred while processing your request.");
 *
 * // Show a warning alert
 * DialogUtils.showWarning("Warning", "This is a warning message.");
 *
 * // Show a confirmation dialog
 * boolean userConfirmed = DialogUtils.showConfirmation("Confirmation", "Are you sure?");
 * if (userConfirmed) {
 *     // User clicked 'Yes'
 *     // Perform the corresponding action
 * } else {
 *     // User clicked 'No' or closed the dialog
 *     // Perform alternative action or do nothing
 * }
 * }
 * </pre>
 *
 * @see javafx.scene.control.Alert
 * @see javafx.scene.control.Alert.AlertType
 */
public class DialogUtils {
    /**
     * Displays an alert dialog with the specified type, title, and content text.
     *
     * @param alertType    The type of the alert (e.g., INFORMATION, ERROR).
     * @param title        The title of the alert.
     * @param contentText  The content text of the alert.
     */
    public static void showAlert(AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Displays an information alert dialog with the specified title and content text.
     *
     * @param title        The title of the information alert.
     * @param contentText  The content text of the information alert.
     */
    public static void showInfo(String title, String contentText) {
        showAlert(AlertType.INFORMATION, title, contentText);
    }

    /**
     * Displays an error alert dialog with the specified title and content text.
     *
     * @param title        The title of the error alert.
     * @param contentText  The content text of the error alert.
     */
    public static void showError(String title, String contentText) {
        showAlert(AlertType.ERROR, title,  contentText);
    }

    /**
     * Displays a warning alert dialog with the specified title and content text.
     *
     * @param title        The title of the warning alert.
     * @param contentText  The content text of the warning alert.
     */
    public static void showWarning(String title, String contentText) {
        showAlert(AlertType.WARNING, title, contentText);
    }

    /**
     * Displays a confirmation dialog with the specified title and content text,
     * allowing the user to confirm or cancel an action.
     *
     * @param title        The title of the confirmation dialog.
     * @param contentText  The content text of the confirmation dialog.
     * @return {@code true} if the user clicks 'Yes', {@code false} if the user clicks 'No' or closes the dialog.
     */
    public static boolean showConfirmation(String title, String contentText) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setContentText(contentText);

        AtomicBoolean confirmationFlag = new AtomicBoolean(false);
        // Customizing button labels
        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                confirmationFlag.set(true);
            }

            if(response==ButtonType.NO){
                confirmationFlag.set(false);
            }
        });
        return confirmationFlag.get();
    }
}
