package com.library.frontend.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogUtils {
    public static void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showInfo(String title, String headerText, String contentText) {
        showAlert(AlertType.INFORMATION, title, headerText, contentText);
    }

    public static void showError(String title, String headerText, String contentText) {
        showAlert(AlertType.ERROR, title, headerText, contentText);
    }

    public static void showWarning(String title, String headerText, String contentText) {
        showAlert(AlertType.WARNING, title, headerText, contentText);
    }

    public static void showConfirmation(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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