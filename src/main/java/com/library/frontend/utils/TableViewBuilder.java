package com.library.frontend.utils;

import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.Genre;
import com.library.database.enums.Role;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TableViewBuilder.class);

    public static void buildBookTableView(TableView<Book> bookTableView) {
        try {
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

            logger.info("Book table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create book table view: {}", e.getMessage());
            throw e;
        }
    }

    public static void buildOperatorTableView(TableView<User> bookTreeTableView) {
        try {
            TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            usernameColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(2));

            TableColumn<User, Role> roleColumn = new TableColumn<>("Role");
            roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
            roleColumn.prefWidthProperty().bind(bookTreeTableView.widthProperty().divide(2));

            bookTreeTableView.getColumns().add(usernameColumn);
            bookTreeTableView.getColumns().add(roleColumn);

            logger.info("Operator table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create operator table view: {}", e.getMessage());
            throw e;
        }
    }
}
