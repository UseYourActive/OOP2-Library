package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableViews.TableViewBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

@NoArgsConstructor
public class AdministratorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);
    @FXML public Button switchButton;
    @FXML public Button scrapBookButton;
    @FXML public Button registerBookButton;
    @FXML public Button loadBooksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea resumeTextArea;
    @FXML public TableView<Book> booksTableView;
    @FXML public AnchorPane anchorPane;

    private AdminService adminService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> switchButton.requestFocus());

        adminService=(AdminService) ServiceFactory.getService(AdminService.class);

        TableViewBuilder.buildBookTableView(booksTableView);
        updateTableView(adminService.getBooks());
    }

    private void updateTableView(List<Book> bookList){
        booksTableView.getItems().clear();
        booksTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
    }

    @FXML
    public void registerBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/registerNewBookScene.fxml","Register new book");
    }

    @FXML
    public void loadBooksButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/addBooksScene.fxml","Register new book");
    }
    @FXML
    public void scrapBookButtonOnMouseClicked() {
        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if(selectedBook !=null){
            adminService.removeBook(selectedBook);

            resumeTextArea.clear();
            updateTableView(adminService.getBooks());
        }
    }
    @FXML
    public void searchBookButtonOnMouseClicked() {
        List<Book> results=new ArrayList<>();
        List<Book> bookList=adminService.getBooks();
        String stringToSearch=searchBookTextField.getText();

        if(!stringToSearch.isEmpty())
        {
            results.addAll(bookList.stream().filter(book -> book.getTitle().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getAuthor().getName().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getGenre().getValue().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getPublishYear().toString().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getResume().contains(stringToSearch)).toList());
        }
        updateTableView(results);
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml", SceneLoader.getStage().getTitle());
    }

    @FXML
    public void booksTableViewOnClicked() {
        if(!booksTableView.getSelectionModel().isEmpty()) {
            scrapBookButton.setDisable(false);
            loadBooksButton.setDisable(false);
        }
        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if(selectedBook!=null)
            resumeTextArea.setText(selectedBook.toString());
    }

    public void anchorPaneOnMouseClicked() {
        Platform.runLater(() -> anchorPane.requestFocus());
    }
}
