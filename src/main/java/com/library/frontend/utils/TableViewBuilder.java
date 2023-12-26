package com.library.frontend.utils;

import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.entities.Reader;
import com.library.database.entities.User;
import com.library.database.enums.Genre;
import com.library.database.enums.Rating;
import com.library.database.enums.Role;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TableViewBuilder.class);

    public static void createInventoryTableViewColumns(TableView<BookInventory> inventoryTableView) {
        try {
            TableColumn<BookInventory, String> titleTableColumn = new TableColumn<>("Title");
            titleTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().getTitle()));
            titleTableColumn.prefWidthProperty().bind(inventoryTableView.widthProperty().divide(3));

            TableColumn<BookInventory, String> authorTableColumn = new TableColumn<>("Author");
            authorTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().getAuthor().toString())); // Assuming getAuthor() returns a String
            authorTableColumn.prefWidthProperty().bind(inventoryTableView.widthProperty().divide(3));

            TableColumn<BookInventory, Genre> genreTableColumn = new TableColumn<>("Genre");
            genreTableColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBook().getGenre()));
            genreTableColumn.prefWidthProperty().bind(inventoryTableView.widthProperty().divide(3));

            inventoryTableView.getColumns().add(titleTableColumn);
            inventoryTableView.getColumns().add(authorTableColumn);
            inventoryTableView.getColumns().add(genreTableColumn);

            logger.info("Book Inventory table view created successfully");
        } catch (Exception e) {
            logger.error("Failed to create book inventory table view: {}", e.getMessage());
            throw e;
        }
    }

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
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            readerTableView.getColumns().add(firstNameColumn);

            TableColumn<Reader, String> lastNameColumn = new TableColumn<>("Last Name");
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            readerTableView.getColumns().add(lastNameColumn);

            TableColumn<Reader, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            readerTableView.getColumns().add(emailColumn);

            TableColumn<Reader, String> phoneNumberColumn = new TableColumn<>("Phone Number");
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            readerTableView.getColumns().add(phoneNumberColumn);

            TableColumn<Reader, String> ratingColumn = new TableColumn<>("Rating");
            ratingColumn.setCellValueFactory(cellData -> {
                Rating rating = cellData.getValue().getRating();
                return new SimpleStringProperty(rating != null ? rating.toString() : "");
            });
            readerTableView.getColumns().add(ratingColumn);

            logger.info("Reader table view columns created successfully");
        } catch (Exception e) {
            logger.error("Failed to create reader table view columns: {}", e.getMessage());
            throw e;
        }
    }
}
