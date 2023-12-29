package com.library.frontend.utils.tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code BookTableViewBuilder} class implements the {@link TableViewBuilder} interface
 * to create and configure JavaFX {@link javafx.scene.control.TableView} columns for the {@link Book} entity.
 * It defines columns for book properties such as ID, number of times used, and status.
 *
 * @see TableViewBuilder
 */
public class BookTableViewBuilder implements TableViewBuilder<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookTableViewBuilder.class);

    /**
     * Creates and configures the table columns for the specified {@link javafx.scene.control.TableView}.
     * Defines columns for book ID, number of times used, and status.
     *
     * @param tableView The TableView for which columns are created and configured.
     */
    @Override
    public void createTableViewColumns(TableView<Book> tableView) {
        try {
            TableColumn<Book, Long> idTableColumn = new TableColumn<>("Id");
            idTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
            idTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Book, Integer> timesUsedTableColumn = new TableColumn<>("Times used");
            timesUsedTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumberOfTimesUsed()));
            timesUsedTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Book, BookStatus> statusTableColumn = new TableColumn<>("Status");
            statusTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBookStatus()));
            statusTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            tableView.getColumns().add(idTableColumn);
            tableView.getColumns().add(timesUsedTableColumn);
            tableView.getColumns().add(statusTableColumn);

            logger.info("Book Inventory table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create book inventory table view: {}", e.getMessage());
            throw e;
        }
    }
}
