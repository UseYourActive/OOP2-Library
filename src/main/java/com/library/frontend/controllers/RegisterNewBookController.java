package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterNewBookController implements Controller {
    @FXML public TextField ISBNTextField;
    @FXML public TextField titleTextField;
    @FXML public TextField authorTextField;
    @FXML public TextField yearTextField;
    @FXML public TextArea resumeTextField;
    @FXML public ComboBox<Genre> genreComboBox;
    @FXML public Button registerButton;

    @FXML public Button cancelButton;
    @FXML public Label informationLabel;
    @FXML public TextField amountTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> genreComboBox.requestFocus());

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }
    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        Book book = getBook();

        ((AdminService) ServiceFactory.getService(AdminService.class)).registerBook(book);

        cancelButtonOnMouseClicked(mouseEvent);
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml", SceneLoader.getStage().getTitle());
    }

    private Book getBook(){
        Author author = Author.builder()
                .name(authorTextField.getText())
                .books(new ArrayList<>())
                .build();

        Genre genre=Genre.getValueOf(String.valueOf(genreComboBox.getSelectionModel().getSelectedItem()));

        return Book.builder()
                .isbn(ISBNTextField.getText())
                .title(titleTextField.getText())
                .author(author)
                .publishYear(Year.parse(yearTextField.getText()))
                .resume(resumeTextField.getText())
                .genre(genre)
                .bookStatus(BookStatus.AVAILABLE)
                .amountOfCopies(Integer.valueOf(amountTextField.getText()))
                .numberOfTimesUsed(0)
                .build();
    }
}
