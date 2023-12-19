package com.library.frontend.utils.tableViews;

import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Year;

public class TableViewBuilder {

    public static void buildBookTableView(TableView<Book> bookTableView){
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        TableColumn<Book, Integer> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        TableColumn<Book, Role> publishYearColumn = new TableColumn<>("Publish Year");
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        publishYearColumn.prefWidthProperty().bind(bookTableView.widthProperty().divide(3));

        bookTableView.getColumns().add(titleColumn);
        bookTableView.getColumns().add(authorColumn);
        bookTableView.getColumns().add(publishYearColumn);
    }

    public static void buildOperatorTableView(TableView<User> bookTreeTableView){
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(3));

        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(3));

        TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(3));

        bookTreeTableView.getColumns().add(idColumn);
        bookTreeTableView.getColumns().add(usernameColumn);
        bookTreeTableView.getColumns().add(roleColumn);
    }
}
