package com.library.frontend.utils.tableviews;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * The {@code TableViewBuilder} interface defines a contract for classes responsible
 * for building and configuring JavaFX {@link javafx.scene.control.TableView} columns.
 * Implementing classes should provide an implementation for the
 * {@link #createTableViewColumns(TableView)} method to define the columns and their
 * properties for a specific type of data.
 * <p>
 * This interface is intended to be used in conjunction with JavaFX TableView components
 * to facilitate the dynamic creation and configuration of table columns based on the
 * provided data type.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a custom implementation of TableViewBuilder for a specific data type
 * public class CustomTableViewBuilder implements TableViewBuilder<CustomData> {
 *     {@literal @}Override
 *     public void createTableViewColumns(TableView<CustomData> tableView) {
 *         // Define and configure table columns for the CustomData type
 *         TableColumn<CustomData, String> nameColumn = new TableColumn<>("Name");
 *         nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
 *
 *         TableColumn<CustomData, Integer> ageColumn = new TableColumn<>("Age");
 *         ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
 *
 *         // Add columns to the TableView
 *         tableView.getColumns().addAll(nameColumn, ageColumn);
 *     }
 * }
 * }
 * </pre>
 *
 * @param <T> The type of data displayed in the TableView.
 * @see javafx.scene.control.TableView
 */
public interface TableViewBuilder<T> {
    Logger logger = LoggerFactory.getLogger(TableViewBuilder.class);
    /**
     * Creates and configures the table columns for the specified {@link javafx.scene.control.TableView}.
     * Implementing classes should define the columns and their properties based on the data type {@code T}.
     *
     * @param tableView The TableView for which columns are created and configured.
     */
    void createTableViewColumns(TableView<T> tableView);

    default void updateTableView(TableView<T> tableView, Collection<T> collection) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(FXCollections.observableArrayList(collection));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }
}

