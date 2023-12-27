package com.library.frontend.utils.tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookTableViewBuilder implements TableViewBuilder<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookTableViewBuilder.class);

    @Override
    public void createTableViewColumns(TableView<Book> tableView) {
        try {
            TableColumn<Book, Long> titleTableColumn = new TableColumn<>("Id");
            titleTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
            titleTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Book, Integer> authorTableColumn = new TableColumn<>("Times used");
            authorTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumberOfTimesUsed()));
            authorTableColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));

            TableColumn<Book, BookStatus> genreTableColumn = new TableColumn<>("Status");
            genreTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBookStatus()));
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