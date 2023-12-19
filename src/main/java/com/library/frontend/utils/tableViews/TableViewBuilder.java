package com.library.frontend.utils.tableViews;

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

    public static void buildBookTreeTableView(TreeTableView<String> bookTreeTableView){
        TreeTableColumn<String, String> titleColumn = new TreeTableColumn<>("title");
        TreeTableColumn<String, String> authorColumn = new TreeTableColumn<>("author");
        TreeTableColumn<String, Year> yearColumn = new TreeTableColumn<>("Publish Year");

        bookTreeTableView.getColumns().add(titleColumn);
        bookTreeTableView.getColumns().add(authorColumn);
        bookTreeTableView.getColumns().add(yearColumn);
    }

    public static void buildOperatorTableView(TableView<User> bookTreeTableView){
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        bookTreeTableView.getColumns().add(idColumn);
        bookTreeTableView.getColumns().add(usernameColumn);
        bookTreeTableView.getColumns().add(roleColumn);
    }
}
