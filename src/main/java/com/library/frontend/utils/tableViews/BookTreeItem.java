package com.library.frontend.utils.tableViews;

import com.library.database.entities.Book;
import javafx.scene.control.TreeItem;

public class BookTreeItem extends TreeItem<String> {
    public BookTreeItem(Book book) {
        super(book.toString());
        createChildren(book);
    }

    private void createChildren(Book book) {
        // Create child items for additional details
        TreeItem<String> resumeItem = new TreeItem<>("Resume: " + book.getResume());
        TreeItem<String> isbnItem = new TreeItem<>("ISBN: " + book.getIsbn());
        TreeItem<String> genreItem = new TreeItem<>("Genre: " + book.getGenre());

        // Add child items to this BookTreeItem
        getChildren().addAll(resumeItem,isbnItem,genreItem);
    }
}