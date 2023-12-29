package com.library.frontend.utils.tableviews;

import com.library.database.entities.Reader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code ReaderTableViewBuilder} class implements the {@link TableViewBuilder} interface
 * to create and configure JavaFX {@link javafx.scene.control.TableView} columns for the {@link Reader} entity.
 * It defines columns for reader-related properties such as first name, last name, and email.
 *
 * @see TableViewBuilder
 */
public class ReaderTableViewBuilder implements TableViewBuilder<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderTableViewBuilder.class);

    /**
     * Creates and configures the table columns for the specified {@link javafx.scene.control.TableView}.
     * Defines columns for reader-related properties such as first name, last name, and email.
     *
     * @param tableView The TableView for which columns are created and configured.
     */
    @Override
    public void createTableViewColumns(TableView<Reader> tableView) {
        try {
            TableColumn<Reader, String> firstNameColumn = new TableColumn<>("First Name");
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            firstNameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Reader, String> lastNameColumn = new TableColumn<>("Last Name");
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            lastNameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Reader, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            emailColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            tableView.getColumns().add(firstNameColumn);
            tableView.getColumns().add(lastNameColumn);
            tableView.getColumns().add(emailColumn);

            logger.info("Reader table view columns created successfully");
        } catch (Exception e) {
            logger.error("Failed to create reader table view columns: {}", e.getMessage());
            throw e;
        }
    }
}
