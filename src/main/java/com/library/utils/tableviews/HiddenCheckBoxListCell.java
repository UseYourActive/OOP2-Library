package com.library.utils.tableviews;

import javafx.scene.control.ListCell;

/**
 * The {@code HiddenCheckBoxListCell} class extends {@code ListCell<T>} and is designed
 * to be used with JavaFX ListView or TableView cells. It hides the checkbox and displays only the text.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a ListView and set the cell factory to use HiddenCheckBoxListCell
 * ListView<String> listView = new ListView<>();
 * listView.setCellFactory(param -> new HiddenCheckBoxListCell<>());
 * }
 * </pre>
 *
 * @param <T> The type of the items in the ListView or TableView.
 * @see javafx.scene.control.ListCell
 * @see javafx.scene.control.ListView
 * @see javafx.scene.control.TableView
 */
public class HiddenCheckBoxListCell<T> extends ListCell<T> {

    /**
     * Updates the item within the cell, hiding the checkbox and displaying only the text.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty A flag indicating whether the cell is empty.
     */
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
