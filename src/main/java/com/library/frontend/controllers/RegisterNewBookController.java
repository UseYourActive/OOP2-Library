package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.validators.ISBNValidator;
import com.library.frontend.utils.validators.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    @FXML public AnchorPane anchorPane;

    private AdminService adminService;

    private Validator validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> genreComboBox.requestFocus());

        adminService=((AdminService) ServiceFactory.getService(AdminService.class));

        genreComboBox.setItems(FXCollections.observableArrayList(Genre.values()));
    }
    @FXML
    public void registerButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            checkInput();

            Book book = getBook();

            adminService.saveBook(book);

            cancelButtonOnMouseClicked(mouseEvent);
        }catch (Exception e){
            informationLabel.setText(e.getMessage());
        }
    }
    @FXML
    public void cancelButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorBooksScene.fxml",SceneLoader.getUsername() + "(Administrator)");
    }

    private void checkInput() throws Exception {
        if(ISBNTextField.getText().isEmpty())
            throw new Exception("Please enter ISBN.");
        validator=new ISBNValidator();

        if(validator.isValid(ISBNTextField.getText()))
            throw new Exception("Please enter valid ISBN code.");

        if(titleTextField.getText().isEmpty())
            throw new Exception("Please enter book title.");

        if(authorTextField.getText().isEmpty())
            throw new Exception("Please enter book author.");

        if(genreComboBox.getSelectionModel().isEmpty())
            throw new Exception("Please choose the genre of the book.");
    }

    private Book getBook(){
        Author author = Author.builder()
                .name(authorTextField.getText())
                .books(new ArrayList<>())
                .build();

        Genre genre=Genre.getValueOf(String.valueOf(genreComboBox.getSelectionModel().getSelectedItem()));

        //not nullable properties are instantiated trough builder
        Book book = Book.builder()
                .isbn(ISBNTextField.getText())
                .title(titleTextField.getText())
                .author(author)
                .genre(genre)
                .bookStatus(BookStatus.AVAILABLE)
                .numberOfTimesUsed(0)
                .resume("")
                .amountOfCopies(0)
                .build();

        //nullable properties need check
        if(!yearTextField.getText().isEmpty())
            book.setPublishYear(Year.parse(yearTextField.getText()));

        if(!resumeTextField.getText().isEmpty()) {
            book.setResume(resumeTextField.getText());
        }

        if(!amountTextField.getText().isEmpty()){
            book.setAmountOfCopies(Integer.valueOf(amountTextField.getText()));
        }

        return book;
    }
}
