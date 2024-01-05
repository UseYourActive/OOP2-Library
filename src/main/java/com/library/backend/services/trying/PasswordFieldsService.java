package com.library.backend.services.trying;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswordFieldsService {

    public void updatePasswordFieldsVisibility(boolean showPassword, PasswordField hiddenField, TextField visibleField) {
        hiddenField.setVisible(!showPassword);
        visibleField.setVisible(showPassword);

        if (showPassword) {
            visibleField.setText(hiddenField.getText());
        } else {
            hiddenField.setText(visibleField.getText());
        }
    }

    public String getPasswordFieldText(PasswordField hiddenField, TextField visibleField) {
        return hiddenField.isVisible() ? hiddenField.getText() : visibleField.getText();
    }
}