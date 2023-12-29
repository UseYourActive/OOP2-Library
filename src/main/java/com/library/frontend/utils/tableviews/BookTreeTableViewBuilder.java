package com.library.frontend.utils.tableviews;

import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class BookTreeTableViewBuilder implements TreeTableViewBuilder<Book>{

    @Override
    public void createTreeTableViewColumns(TreeTableView<Book> bookTreeTableView) {
        //Creating default root node
        TreeItem<Book> root = new TreeItem<>();

        //Creating a column
        TreeTableColumn<Book, String> titleColumn = new TreeTableColumn<>("Book");
        //Defining cell content
        titleColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getTitle())); // Adjust this based on Book attributes
        titleColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.60));
        titleColumn.setResizable(false);

        //Creating a column
        TreeTableColumn<Book, String> bookStatusColumn = new TreeTableColumn<>("Status");
        //Defining cell content
        bookStatusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            BookStatus bookStatus = p.getValue().getValue().getBookStatus();
            if (bookStatus != null) {
                return new ReadOnlyStringWrapper(bookStatus.getDisplayValue());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        }); // Adjust this based on Book attributes
        bookStatusColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.2));
        bookStatusColumn.setResizable(false);

        //Creating a column
        TreeTableColumn<Book, String> numberOfTimesUsedColumn = new TreeTableColumn<>("Times used");
        //Defining cell content
        numberOfTimesUsedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Book, String> p) -> {
            Integer numberOfTimesUsed = p.getValue().getValue().getNumberOfTimesUsed();
            if (numberOfTimesUsed != null) {
                return new ReadOnlyStringWrapper(numberOfTimesUsed.toString());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        }); // Adjust this based on Book attributes
        numberOfTimesUsedColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().multiply(0.2));
        numberOfTimesUsedColumn.setResizable(false);

        bookTreeTableView.setRoot(root);
        bookTreeTableView.setShowRoot(false); // Hide the default root node
        bookTreeTableView.getColumns().add(titleColumn);
        bookTreeTableView.getColumns().add(bookStatusColumn);
        bookTreeTableView.getColumns().add(numberOfTimesUsedColumn);


    }
}
