package com.library.frontend.utils.tableviews;

import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@code TreeTableViewBuilder} interface defines a contract for classes responsible
 * for building and configuring JavaFX {@link javafx.scene.control.TreeTableView} columns.
 * Implementing classes should provide an implementation for the
 * {@link #createTreeTableViewColumns(TreeTableView)} method to define the columns and their
 * properties for a specific type of data in a tree structure.
 * <p>
 * This interface is intended to be used in conjunction with JavaFX TreeTableView components
 * to facilitate the dynamic creation and configuration of tree table columns based on the
 * provided data type.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a custom implementation of TreeTableViewBuilder for a specific data type
 * public class CustomTreeTableViewBuilder implements TreeTableViewBuilder<CustomData> {
 *     {@literal @}Override
 *     public void createTreeTableViewColumns(TreeTableView<CustomData> treeTableView) {
 *         // Define and configure tree table columns for the CustomData type in a tree structure
 *         TreeTableColumn<CustomData, String> nameColumn = new TreeTableColumn<>("Name");
 *         nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
 *
 *         TreeTableColumn<CustomData, Integer> ageColumn = new TreeTableColumn<>("Age");
 *         ageColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("age"));
 *
 *         // Add columns to the TreeTableView
 *         treeTableView.getColumns().addAll(nameColumn, ageColumn);
 *     }
 * }
 * }
 * </pre>
 *
 * @param <T> The type of data displayed in the TreeTableView.
 * @see javafx.scene.control.TreeTableView
 */
public interface TreeTableViewBuilder<T> {
    /**
     * Creates and configures the tree table columns for the specified {@link javafx.scene.control.TreeTableView}.
     * Implementing classes should define the columns and their properties based on the data type {@code T}
     * in a tree structure.
     *
     * @param treeTableView The TreeTableView for which columns are created and configured.
     */
    void createTreeTableViewColumns(TreeTableView<T> treeTableView);

    default TreeItem<T> getSelectedItem(TreeTableView<T> tableView){
        TreeTableView.TreeTableViewSelectionModel<T> selectionModel = tableView.getSelectionModel();
        return  Optional.ofNullable(selectionModel.getSelectedItem()).orElseThrow();
    }

    default List<TreeItem<T>> getSelectedItems(TreeTableView<T> tableView){
        TreeTableView.TreeTableViewSelectionModel<T> selectionModel = Optional.ofNullable(tableView.getSelectionModel()).orElseThrow();
        return Optional.ofNullable(selectionModel.getSelectedItems()).orElseThrow();
    }
}
