package com.library.frontend.utils.tableviews;

import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;

/**
 * The {@code BookTreeTableViewBuilder} class implements the {@link TreeTableViewBuilder} interface
 * to create and configure JavaFX {@link javafx.scene.control.TreeTableView} columns for the {@link Book} entity.
 * It defines columns for book properties such as title, author, genre, status, times used, and published year.
 *
 * @see TreeTableViewBuilder
 */
public class BookTreeTableViewBuilder implements TreeTableViewBuilder<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookTreeTableViewBuilder.class);

    /**
     * Creates and configures the tree table columns for the specified {@link javafx.scene.control.TreeTableView}.
     * Defines columns for book title, author, genre, status, times used, and published year.
     *
     * @param bookTreeTableView The TreeTableView for which columns are created and configured.
     */
    @Override
    public void createTreeTableViewColumns(TreeTableView<Book> bookTreeTableView) {
        logger.info("Creating and configuring tree table columns...");

        TreeItem<Book> root = new TreeItem<>();

        TreeTableColumn<Book, String> titleColumn = new TreeTableColumn<>("Book");
        titleColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting title for book: {}", p.getValue().getValue().getTitle());
            return new ReadOnlyStringWrapper(p.getValue().getValue().getTitle());
        });
        titleColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.3));
        titleColumn.setResizable(false);

        TreeTableColumn<Book, String> authorColumn = new TreeTableColumn<>("Author");
        authorColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting author for book: {}", p.getValue().getValue().getAuthor());
            Author author = p.getValue().getValue().getAuthor();
            if (author != null) {
                return new ReadOnlyStringWrapper(author.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        authorColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.2));
        authorColumn.setResizable(false);

        TreeTableColumn<Book, String> genreColumn = new TreeTableColumn<>("Genre");
        genreColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting genre for book: {}", p.getValue().getValue().getGenre());
            Genre genre = p.getValue().getValue().getGenre();
            if (genre != null) {
                return new ReadOnlyStringWrapper(genre.getValue());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        genreColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        genreColumn.setResizable(false);

        TreeTableColumn<Book, String> publishedYearColumn = new TreeTableColumn<>("Year");
        publishedYearColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting published year for book: {}", p.getValue().getValue().getPublishYear());
            Year publishedYear = p.getValue().getValue().getPublishYear();
            if (publishedYear != null) {
                return new ReadOnlyStringWrapper(publishedYear.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        publishedYearColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        publishedYearColumn.setResizable(false);

        TreeTableColumn<Book, String> idColumn = new TreeTableColumn<>("ID");
        idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting book status for book: {}", p.getValue().getValue().getBookStatus());
            return new ReadOnlyStringWrapper(p.getValue().getValue().getId().toString());
        });
        idColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        idColumn.setResizable(false);

        TreeTableColumn<Book, String> bookStatusColumn = new TreeTableColumn<>("Status");
        bookStatusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            logger.debug("Getting book status for book: {}", p.getValue().getValue().getBookStatus());
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
            logger.debug("Getting number of times used for book: {}", p.getValue().getValue().getNumberOfTimesUsed());
            Integer numberOfTimesUsed = p.getValue().getValue().getNumberOfTimesUsed();
            if (numberOfTimesUsed != null) {
                return new ReadOnlyStringWrapper(numberOfTimesUsed.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        numberOfTimesUsedColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        numberOfTimesUsedColumn.setResizable(false);


        logger.info("Tree table columns created and configured successfully.");

        bookTreeTableView.setRoot(root);
        bookTreeTableView.setShowRoot(false);
        bookTreeTableView.getColumns().add(titleColumn);
        bookTreeTableView.getColumns().add(authorColumn);
        bookTreeTableView.getColumns().add(genreColumn);
        bookTreeTableView.getColumns().add(publishedYearColumn);
        bookTreeTableView.getColumns().add(idColumn);
        bookTreeTableView.getColumns().add(bookStatusColumn);
        bookTreeTableView.getColumns().add(numberOfTimesUsedColumn);
    }
}
