package com.library.backend.services.trying;

import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class ContextMenuService {
    public static ContextMenu createOperatorContextMenu(AdminOperatorService adminOperatorService,
                                                        TableViewBuilder<User> operatorTableViewBuilder,
                                                        TableView<User> operatorTableView) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem removeOperator = new MenuItem("Remove");
        MenuItem createOperator = new MenuItem("Create operator");

        contextMenu.getItems().addAll(createOperator, removeOperator);

        removeOperator.setOnAction(event -> removeSelectedOperator(adminOperatorService, operatorTableViewBuilder, operatorTableView));
        createOperator.setOnAction(event -> adminOperatorService.createOperator());

        return contextMenu;
    }

    public static ContextMenu createBookContextMenu(TableView<Book> bookTableView,
                                                    EventHandler<ActionEvent> archiveHandler,
                                                    EventHandler<ActionEvent> removeHandler) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem archiveBook = new MenuItem("Archive book");
        MenuItem removeBook = new MenuItem("Remove book");

        archiveBook.setOnAction(archiveHandler);
        removeBook.setOnAction(removeHandler);

        contextMenu.getItems().addAll(archiveBook, removeBook);

        return contextMenu;
    }

    private static void removeSelectedOperator(AdminOperatorService adminOperatorService,
                                               TableViewBuilder<User> operatorTableViewBuilder,
                                               TableView<User> operatorTableView) {
        User operator = operatorTableView.getSelectionModel().getSelectedItem();

        if (operator != null) {
            adminOperatorService.removeOperator(operator);
            operatorTableViewBuilder.updateTableView(operatorTableView, adminOperatorService.getAllOperators());
        }
    }
}
