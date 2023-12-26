package com.library.frontend.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.concurrent.atomic.AtomicBoolean;

public class DialogUtils {
    public static void showAlert(AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showInfo(String title, String contentText) {
        showAlert(AlertType.INFORMATION, title, contentText);
    }

    public static void showError(String title, String contentText) {
        showAlert(AlertType.ERROR, title,  contentText);
    }

    public static void showWarning(String title, String contentText) {
        showAlert(AlertType.WARNING, title, contentText);
    }

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

//Examples

// Show an error dialog
//DialogUtils.showError("Error", "An error occurred", "This is the error message.");

// Show an information dialog
//DialogUtils.showInfo("Information", "Information", "This is some information.");

// Show a warning dialog
//DialogUtils.showWarning("Warning", "Warning", "This is a warning message.");

// Show a confirmation dialog
//DialogUtils.showConfirmation("Confirmation", "Confirmation", "Are you sure you want to proceed?");