package com.library.frontend.utils.tableviews;

import com.library.database.entities.User;
import com.library.database.enums.Role;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorTableViewBuilder implements TableViewBuilder<User> {
    private static final Logger logger = LoggerFactory.getLogger(OperatorTableViewBuilder.class);

    @Override
    public void createTableViewColumns(TableView<User> tableView) {
        try {
            TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            usernameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(2));

            TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
            roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
            roleColumn.prefWidthProperty().bind(tableView.widthProperty().divide(2));

            tableView.getColumns().add(usernameColumn);
            tableView.getColumns().add(roleColumn);

            logger.info("Operator table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create operator table view: {}", e.getMessage());
            throw e;
        }
    }
}
