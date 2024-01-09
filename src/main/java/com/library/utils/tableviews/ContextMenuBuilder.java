package com.library.utils.tableviews;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Map;

/**
 * The {@code ContextMenuBuilder} class provides a convenient way to create a JavaFX ContextMenu
 * with specified menu items and their corresponding ActionEvent handlers.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a map of menu items with their corresponding ActionEvent handlers
 * Map<String, EventHandler<ActionEvent>> menuItems = new HashMap<>();
 * menuItems.put("Open", e -> openFile());
 * menuItems.put("Delete", e -> deleteItem());
 *
 * // Create a ContextMenu using the ContextMenuBuilder
 * ContextMenu contextMenu = ContextMenuBuilder.prepareContextMenu(menuItems);
 * }
 * </pre>
 *
 * @see javafx.scene.control.ContextMenu
 * @see javafx.scene.control.MenuItem
 * @see javafx.event.ActionEvent
 */
public class ContextMenuBuilder {

    /**
     * Prepares and returns a ContextMenu with menu items and their corresponding ActionEvent handlers.
     *
     * @param menuItems A map where the keys are the labels of the menu items, and the values are
     *                  the corresponding ActionEvent handlers.
     * @return A ContextMenu with the specified menu items and handlers.
     */
    public static ContextMenu prepareContextMenu(Map<String, EventHandler<ActionEvent>> menuItems) {
        ContextMenu contextMenu = new ContextMenu();

        for (Map.Entry<String, EventHandler<ActionEvent>> entry : menuItems.entrySet()) {
            MenuItem menuItem = new MenuItem(entry.getKey());
            menuItem.setOnAction(entry.getValue());
            contextMenu.getItems().add(menuItem);
        }

        return contextMenu;
    }
}
