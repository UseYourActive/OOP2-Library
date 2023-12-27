package com.library.frontend.utils.tableviews;

import com.library.database.entities.BookInventory;
import com.library.database.enums.Genre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryTableViewBuilder implements TableViewBuilder<BookInventory> {
    private static final Logger logger = LoggerFactory.getLogger(InventoryTableViewBuilder.class);

    @Override
    public void createTableViewColumns(TableView<BookInventory> tableView) {
        try {
            TableColumn<BookInventory, String> titleTableColumn = new TableColumn<>("Title");
            titleTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRepresentiveBook().getTitle()));
            titleTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<BookInventory, String> authorTableColumn = new TableColumn<>("Author");
            authorTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRepresentiveBook().getAuthor().toString())); // Assuming getAuthor() returns a String
            authorTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<BookInventory, Genre> genreTableColumn = new TableColumn<>("Genre");
            genreTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRepresentiveBook().getGenre()));
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
