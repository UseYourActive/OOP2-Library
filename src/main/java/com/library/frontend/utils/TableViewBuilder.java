package com.library.frontend.utils;

import com.library.database.entities.Book;
import com.library.database.entities.Reader;
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

    public static void createBookTableViewColumns(TableView<Book> bookTableView) {
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

    public static void createOperatorTableViewColumns(TableView<User> bookTreeTableView) {
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

    public static void createReaderTableViewColumns(TableView<Reader> readerTableView){
        try {
            TableColumn<Reader, String> firstNameColumn = new TableColumn<>("First Name");
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            firstNameColumn.prefWidthProperty().bind(readerTableView.widthProperty().divide(2));

            TableColumn<Reader, Role> lastNameColumn = new TableColumn<>("Last name");
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            lastNameColumn.prefWidthProperty().bind(readerTableView.widthProperty().divide(2));

            readerTableView.getColumns().add(firstNameColumn);
            readerTableView.getColumns().add(lastNameColumn);

            logger.info("Operator table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create operator table view: {}", e.getMessage());
            throw e;
        }
    }
}
