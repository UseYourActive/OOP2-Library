package com.library.frontend.utils.tableviews;

import javafx.scene.control.TableView;

public interface TableViewBuilder<T> {
    void createTableViewColumns(TableView<T> tableView);
}
