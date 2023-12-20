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

        resumeTextArea.setFocusTraversable(false);

        TableViewBuilder.buildBookTableView(booksTableView);//Load columns
        updateTableView(adminService.getBooks()); //populate table
    }

    //Mouse click event
    private void checkAndUpdateButtons(MouseEvent mouseEvent) {

        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        double textFieldMinX = booksTableView.localToScene(booksTableView.getBoundsInLocal()).getMinX();
        double textFieldMinY = booksTableView.localToScene(booksTableView.getBoundsInLocal()).getMinY();
        double textFieldMaxX = booksTableView.localToScene(booksTableView.getBoundsInLocal()).getMaxX();
        double textFieldMaxY = booksTableView.localToScene(booksTableView.getBoundsInLocal()).getMaxY();

        if (mouseX >= textFieldMinX && mouseX <= textFieldMaxX && mouseY >= textFieldMinY && mouseY <= textFieldMaxY) {
            if(!booksTableView.getSelectionModel().isEmpty()){
                scrapBookButton.setDisable(false);
                loadBooksButton.setDisable(false);
            }
        }else {
            scrapBookButton.setDisable(true);
            loadBooksButton.setDisable(true);
            resumeTextArea.clear();
            booksTableView.getSelectionModel().clearSelection();
        }
    }

    private void updateTableView(Collection<Book> bookList){
        booksTableView.getItems().clear();
        resumeTextArea.clear();
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
    public void scrapBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        //checkAndUpdateButtons(mouseEvent);
        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if(selectedBook !=null){
            adminService.removeBook(selectedBook);

            updateTableView(adminService.getBooks());
        }
    }
    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        Set<Book> results=new HashSet<>();
        List<Book> bookList=adminService.getBooks();
        String stringToSearch=searchBookTextField.getText();

        if(stringToSearch.isEmpty())
        {
            updateTableView(bookList);
        }else {
            results.addAll(bookList.stream().filter(book -> book.getTitle().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getAuthor().getName().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getGenre().getValue().contains(stringToSearch)).toList());
            //results.addAll(bookList.stream().filter(book -> book.getPublishYear().toString().contains(stringToSearch)).toList());
            results.addAll(bookList.stream().filter(book -> book.getResume().contains(stringToSearch)).toList());
            updateTableView(results);
        }
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml", SceneLoader.getStage().getTitle());
    }

    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);

        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if(selectedBook!=null)
            resumeTextArea.setText(selectedBook.toString());
    }

    @FXML
    public void anchorPaneOnMouseClicked(MouseEvent mouseEvent) {
        Platform.runLater(() -> anchorPane.requestFocus());
        checkAndUpdateButtons(mouseEvent);
    }

    public void resumeTextAreaButtonOnMouseClicked(MouseEvent mouseEvent) {
        checkAndUpdateButtons(mouseEvent);
    }
}
