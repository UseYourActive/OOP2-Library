package com.library.frontend.utils.tableviews;

import javafx.scene.control.TreeTableView;

public interface TreeTableViewBuilder <T>{

    void createTreeTableViewColumns(TreeTableView<T> treeTableView);
}
