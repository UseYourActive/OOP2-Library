package com.library.frontend.utils.tableViews;

import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.Genre;
import com.library.database.enums.Role;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Year;

public class TableViewBuilder {

    public static void buildBookTableView(TableView<Book> bookTableView){
        TableColumn<Book, String> titleTableColumn = new TableColumn<>("Title");
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleTableColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        TableColumn<Book, Integer> authorTableColumn = new TableColumn<>("Author");
        authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorTableColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        TableColumn<Book, Genre> genreTableColumn = new TableColumn<>("Genre");
        genreTableColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreTableColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        bookTableView.getColumns().add(titleTableColumn);
        bookTableView.getColumns().add(authorTableColumn);
        bookTableView.getColumns().add(genreTableColumn);
    }

    public static void buildOperatorTableView(TableView<User> bookTreeTableView){
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(2));

        TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(2));

        bookTreeTableView.getColumns().add(usernameColumn);
        bookTreeTableView.getColumns().add(roleColumn);
    }
}
