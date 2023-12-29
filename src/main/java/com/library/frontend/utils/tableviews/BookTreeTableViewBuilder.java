package com.library.frontend.utils.tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.Year;

public class BookTreeTableViewBuilder implements TreeTableViewBuilder<Book>{

    @Override
    public void createTreeTableViewColumns(TreeTableView<Book> bookTreeTableView) {
        //Creating default root node
        TreeItem<Book> root = new TreeItem<>();

        TreeTableColumn<Book, String> titleColumn = new TreeTableColumn<>("Book");
        setNonNullColumnPreferences(bookTreeTableView,titleColumn,0.3);

        TreeTableColumn<Book, String> authorColumn = new TreeTableColumn<>("Author");
        setNonNullColumnPreferences(bookTreeTableView,authorColumn,0.2);

        TreeTableColumn<Book, String> genreColumn = new TreeTableColumn<>("Genre");
        setNonNullColumnPreferences(bookTreeTableView,genreColumn,0.2);



        TreeTableColumn<Book, String> bookStatusColumn = new TreeTableColumn<>("Status");
        bookStatusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            BookStatus bookStatus = p.getValue().getValue().getBookStatus();
            if (bookStatus != null) {
                return new ReadOnlyStringWrapper(bookStatus.getDisplayValue());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        }); // Adjust this based on Book attributes
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
        }); // Adjust this based on Book attributes
        numberOfTimesUsedColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        numberOfTimesUsedColumn.setResizable(false);

        TreeTableColumn<Book, String> publishedYearColumn = new TreeTableColumn<>("Year");
        numberOfTimesUsedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            Year publishedYear = p.getValue().getValue().getPublishYear();
            if (publishedYear != null) {
                return new ReadOnlyStringWrapper(publishedYear.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        }); // Adjust this based on Book attributes
        numberOfTimesUsedColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.1));
        numberOfTimesUsedColumn.setResizable(false);


        bookTreeTableView.setRoot(root);
        bookTreeTableView.setShowRoot(false); // Hide the default root node
        bookTreeTableView.getColumns().add(titleColumn);
        bookTreeTableView.getColumns().add(authorColumn);
        bookTreeTableView.getColumns().add(genreColumn);
        bookTreeTableView.getColumns().add(bookStatusColumn);
        bookTreeTableView.getColumns().add(publishedYearColumn);
        bookTreeTableView.getColumns().add(numberOfTimesUsedColumn);

    }

    //Defining cell content
    private void setNonNullColumnPreferences(TreeTableView<Book> treeTableView,TreeTableColumn<Book, String> column,Double multiplier){
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getTitle())); // Adjust this based on Book attributes
        column.prefWidthProperty().bind(treeTableView.widthProperty().multiply(multiplier));
        column.setResizable(false);
    }

}
