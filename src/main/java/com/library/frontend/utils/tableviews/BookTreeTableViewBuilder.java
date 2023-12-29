package com.library.frontend.utils.tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.Year;

/**
 * The {@code BookTreeTableViewBuilder} class implements the {@link TreeTableViewBuilder} interface
 * to create and configure JavaFX {@link javafx.scene.control.TreeTableView} columns for the {@link Book} entity.
 * It defines columns for book properties such as title, author, genre, status, times used, and published year.
 *
 * @see TreeTableViewBuilder
 */
public class BookTreeTableViewBuilder implements TreeTableViewBuilder<Book> {

    /**
     * Creates and configures the tree table columns for the specified {@link javafx.scene.control.TreeTableView}.
     * Defines columns for book title, author, genre, status, times used, and published year.
     *
     * @param bookTreeTableView The TreeTableView for which columns are created and configured.
     */
    @Override
    public void createTreeTableViewColumns(TreeTableView<Book> bookTreeTableView) {
        // Creating default root node
        TreeItem<Book> root = new TreeItem<>();

        TreeTableColumn<Book, String> titleColumn = new TreeTableColumn<>("Book");
        setNonNullColumnPreferences(bookTreeTableView, titleColumn, 0.3);

        TreeTableColumn<Book, String> authorColumn = new TreeTableColumn<>("Author");
        setNonNullColumnPreferences(bookTreeTableView, authorColumn, 0.2);

        TreeTableColumn<Book, String> genreColumn = new TreeTableColumn<>("Genre");
        setNonNullColumnPreferences(bookTreeTableView, genreColumn, 0.2);

        TreeTableColumn<Book, String> bookStatusColumn = new TreeTableColumn<>("Status");
        bookStatusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            BookStatus bookStatus = p.getValue().getValue().getBookStatus();
            if (bookStatus != null) {
                return new ReadOnlyStringWrapper(bookStatus.getDisplayValue());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        bookStatusColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        bookStatusColumn.setResizable(false);

        TreeTableColumn<Book, String> numberOfTimesUsedColumn = new TreeTableColumn<>("Times used");
        numberOfTimesUsedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            Integer numberOfTimesUsed = p.getValue().getValue().getNumberOfTimesUsed();
            if (numberOfTimesUsed != null) {
                return new ReadOnlyStringWrapper(numberOfTimesUsed.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        numberOfTimesUsedColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        numberOfTimesUsedColumn.setResizable(false);

        TreeTableColumn<Book, String> publishedYearColumn = new TreeTableColumn<>("Year");
        publishedYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            Year publishedYear = p.getValue().getValue().getPublishYear();
            if (publishedYear != null) {
                return new ReadOnlyStringWrapper(publishedYear.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        publishedYearColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        publishedYearColumn.setResizable(false);

        bookTreeTableView.setRoot(root);
        bookTreeTableView.setShowRoot(false); // Hide the default root node
        bookTreeTableView.getColumns().add(titleColumn);
        bookTreeTableView.getColumns().add(authorColumn);
        bookTreeTableView.getColumns().add(genreColumn);
        bookTreeTableView.getColumns().add(bookStatusColumn);
        bookTreeTableView.getColumns().add(publishedYearColumn);
        bookTreeTableView.getColumns().add(numberOfTimesUsedColumn);
    }

    // Defining cell content
    private void setNonNullColumnPreferences(TreeTableView<Book> treeTableView, TreeTableColumn<Book, String> column, Double multiplier) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getTitle())); // Adjust this based on Book attributes
        column.prefWidthProperty().bind(treeTableView.widthProperty().multiply(multiplier));
        column.setResizable(false);
    }
}
