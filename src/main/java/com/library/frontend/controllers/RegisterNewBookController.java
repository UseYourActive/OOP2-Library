package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> genreComboBox.requestFocus());
        //FXCollections.observableList(Genre.stringValues)
        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }
    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        Author author = Author.builder()
                .name(authorTextField.getText())
                .build();

        Book book= Book.builder()
                .isbn(ISBNTextField.getText())
                .title(titleTextField.getText())
                .author(author)
                .publishYear(Year.parse(yearTextField.getText()))
                .resume(resumeTextField.getText())
                .genre(Genre.getValueOf(String.valueOf(genreComboBox.getSelectionModel().getSelectedItem())))
                .bookStatus(BookStatus.AVAILABLE)
                .build();

        Book.setAmountOfCopies(0);

        AdminService service= (AdminService) ServiceFactory.getService(AdminService.class);
        service.registerBook(book);

        cancelButtonOnMouseClicked(mouseEvent);
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml", SceneLoader.getStage().getTitle());
    }
}
