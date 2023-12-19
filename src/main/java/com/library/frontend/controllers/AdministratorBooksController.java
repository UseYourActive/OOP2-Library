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
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> switchButton.requestFocus());
        populateTableView();
    }

    private void populateTableView(){

        TableViewBuilder.buildBookTableView(booksTableView);

        AdminService service= (AdminService) ServiceFactory.getService(AdminService.class);
        List<Book> bookList=service.getBooks();
        booksTableView.getItems().addAll(FXCollections.observableArrayList(bookList));
        booksTableView.refresh();
        SceneLoader.getStage().getScene().getRoot().requestLayout();
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
        ((AdminService)ServiceFactory.getService(AdminService.class)).removeBook(selectedBook);
    }
    @FXML
    public void searchBookButtonOnMouseClicked() {
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
        resumeTextArea.setText(selectedBook.toString());

    }
}
