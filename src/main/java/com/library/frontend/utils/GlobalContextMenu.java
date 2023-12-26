package com.library.frontend.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class GlobalContextMenu {
    private ContextMenu contextMenu;
    private TableView<?> attachedTableView;

    public GlobalContextMenu() {
        contextMenu = new ContextMenu();
    }

    public void addAction(String text, Runnable action) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(event -> action.run());
        contextMenu.getItems().add(menuItem);
    }

    public void attachToNode(Node node) {
        if (node instanceof TableView) {
            attachedTableView = (TableView<?>) node;
            attachedTableView.setContextMenu(contextMenu);
        } else {
            attachedTableView = null;
            contextMenu.hide();
        }
    }
}



