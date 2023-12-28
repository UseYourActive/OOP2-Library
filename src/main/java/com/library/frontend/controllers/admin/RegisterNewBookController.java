package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterNewBookController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RegisterNewBookController.class);
    @FXML public TextField titleTextField;
    @FXML public TextField authorTextField;
    @FXML public TextField yearTextField;
    @FXML public TextArea resumeTextField;
    @FXML public ComboBox<Genre> genreComboBox;
    @FXML public Button registerButton;
    @FXML public Button cancelButton;
    @FXML public Label informationLabel;
    @FXML public TextField amountTextField;
    @FXML public AnchorPane anchorPane;
    private AdminService adminService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> genreComboBox.requestFocus());

        adminService = ((AdminService) ServiceFactory.getService(AdminService.class));

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }

    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            checkInput();

            BookInventoryRepository bookInventoryRepository = BookInventoryRepository.getInstance();

            List<Book> bookList = new ArrayList<>();
            int quantity = getQuantity();
            BookInventory bookInventory = BookInventory.builder().build();

            Book representativeBook = getBook();

            boolean flag = true;
            // Checks if there is already such inventory
            // If true, increase the quantity
            for (BookInventory bookInventory1 : bookInventoryRepository.findAll()) {
                if (bookInventory1.getRepresentiveBook().equalsBook(representativeBook)) {

                    for (int i = 0; i < quantity; i++) {
                        Book book = getBook();

                        book.setInventory(bookInventory1);

                        adminService.saveBook(book);

                        bookInventory1.addBook(book);
                    }
                    adminService.saveInventory(bookInventory1);

                    flag = false;
                    break;
                }
            }

            // If else creates new inventory
            if (flag) {
                for (int i = 0; i < quantity; i++) {
                    Book book = getBook();

                    bookInventory.setRepresentiveBook(book);
                    book.setInventory(bookInventory);

                    adminService.saveBook(book);

                    bookList.add(book);
                }

                bookInventory.setBookList(bookList);

                adminService.saveInventory(bookInventory);
            }

            cancelButtonOnMouseClicked(mouseEvent);
        } catch (Exception e) {
            informationLabel.setText(e.getMessage());
            logger.error("Error occurred during book registration", e);
        }
    }

    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent, "/views/administratorBooksScene.fxml", SceneLoader.getUsername() + "(Administrator)");
    }

    private void checkInput() throws Exception {
        if (titleTextField.getText().isEmpty())
            throw new Exception("Please enter book title.");

        if (authorTextField.getText().isEmpty())
            throw new Exception("Please enter book author.");

        if (genreComboBox.getSelectionModel().isEmpty())
            throw new Exception("Please choose the genre of the book.");
    }

    private int getQuantity() {
        if (amountTextField.getText().isEmpty())
            return 1;
        else
            return Integer.parseInt(amountTextField.getText());
    }

    private Book getBook() {
        AuthorRepository authorRepository = AuthorRepository.getInstance();

        Author author = authorRepository.findByName(authorTextField.getText()).orElseGet(() ->
                Author.builder()
                        .name(authorTextField.getText())
                        .books(new ArrayList<>())
                        .build()
        );

        Genre genre = Genre.getValueOf(String.valueOf(genreComboBox.getSelectionModel().getSelectedItem()));

        // Not nullable properties are instantiated through builder
        Book book = Book.builder()
                .title(titleTextField.getText())
                .author(author)
                .genre(genre)
                .bookStatus(BookStatus.AVAILABLE)
                .numberOfTimesUsed(0)
                .resume("")
                .build();

        // Nullable properties need a check
        if (!yearTextField.getText().isEmpty())
            book.setPublishYear(Year.parse(yearTextField.getText()));

        if (!resumeTextField.getText().isEmpty()) {
            book.setResume(resumeTextField.getText());
        }

        return book;
    }
}
