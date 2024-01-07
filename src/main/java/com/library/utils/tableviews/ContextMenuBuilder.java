package com.library.utils.tableviews;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Map;

public class ContextMenuBuilder {
    public static ContextMenu prepareContextMenu(Map<String,EventHandler<ActionEvent>> menuItems) {
        ContextMenu contextMenu = new ContextMenu();

        for(Map.Entry<String,EventHandler<ActionEvent>> entry: menuItems.entrySet()){
            MenuItem menuItem= new MenuItem(entry.getKey());
            menuItem.setOnAction(entry.getValue());
            contextMenu.getItems().add(menuItem);
        }

        return contextMenu;
    }
}
