package com.library.utils.tableviews;

import com.library.database.entities.BookInventory;
import com.library.database.enums.Genre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code InventoryTableViewBuilder} class implements the {@link TableViewBuilder} interface
 * to create and configure JavaFX {@link javafx.scene.control.TableView} columns for the {@link BookInventory} entity.
 * It defines columns for book inventory properties such as title, author, and genre.
 *
 * @see TableViewBuilder
 */
public class InventoryTableViewBuilder implements TableViewBuilder<BookInventory> {
    private static final Logger logger = LoggerFactory.getLogger(InventoryTableViewBuilder.class);

    /**
     * Creates and configures the table columns for the specified {@link javafx.scene.control.TableView}.
     * Defines columns for book inventory title, author, and genre.
     *
     * @param tableView The TableView for which columns are created and configured.
     */
    @Override
    public void createTableViewColumns(TableView<BookInventory> tableView) {
        try {
            TableColumn<BookInventory, String> titleTableColumn = new TableColumn<>("Title");
            titleTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRepresentativeBook().getTitle()));
            titleTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<BookInventory, String> authorTableColumn = new TableColumn<>("Author");
            authorTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRepresentativeBook().getAuthor().toString())); // Assuming getAuthor() returns a String
            authorTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<BookInventory, Genre> genreTableColumn = new TableColumn<>("Genre");
            genreTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRepresentativeBook().getGenre()));
            genreTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            tableView.getColumns().add(titleTableColumn);
            tableView.getColumns().add(authorTableColumn);
            tableView.getColumns().add(genreTableColumn);

            logger.info("Book Inventory table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create book inventory table view: {}", e.getMessage());
            throw e;
        }
    }
}
