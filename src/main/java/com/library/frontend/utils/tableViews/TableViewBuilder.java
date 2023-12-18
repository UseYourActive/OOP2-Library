package com.library.frontend.utils.tableViews;

import com.library.database.entities.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.Year;

public class TableViewBuilder {

    public static void buildBookTreeTableView(TreeTableView<String> bookTreeTableView){
        TreeTableColumn<String, String> titleColumn = new TreeTableColumn<>("Title");
        TreeTableColumn<String, String> authorColumn = new TreeTableColumn<>("Author");
        TreeTableColumn<String, Year> yearColumn = new TreeTableColumn<>("Publish Year");
        bookTreeTableView.getColumns().addAll(titleColumn, authorColumn, yearColumn);
    }

    public static void buildOperatorTableView(TableView<User> bookTreeTableView){
        TableColumn<User, Integer> id = new TableColumn<>("ID");
        TableColumn<User, String> username = new TableColumn<>("USERNAME");
        bookTreeTableView.getColumns().addAll(id, username);
    }
}
