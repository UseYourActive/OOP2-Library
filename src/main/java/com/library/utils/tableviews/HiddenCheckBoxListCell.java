package com.library.utils.tableviews;

import javafx.scene.control.ListCell;

public class HiddenCheckBoxListCell<T> extends ListCell<T> {
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            // Hide the checkbox and display just the text
            setGraphic(null);
            setText(item.toString());
        } else {
            setGraphic(null);
            setText(null);
        }
    }
}