package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

@NoArgsConstructor
public class AdministratorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);
    @FXML public Button switchButton;
    @FXML public Button registerBookButton;
    @FXML public Button loadBooksButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea resumeTextArea;
    @FXML public TableView<Book> booksTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button removeBookButton;

    private AdminService adminService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switchButton.requestFocus();

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
                removeBookButton.setDisable(false);
                loadBooksButton.setDisable(false);
            }
        }else {
            removeBookButton.setDisable(true);
            loadBooksButton.setDisable(true);
            resumeTextArea.clear();
            booksTableView.getSelectionModel().clearSelection();
        }

        anchorPane.requestFocus();
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
    public void loadBooksButtonOnMouseClicked() {
        openDialog();
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
            results.addAll(bookList.stream()
                    .filter(book -> book.getTitle().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getAuthor().getName().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getGenre().getValue().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> Objects.nonNull(book.getPublishYear()))
                    .filter(book -> book.getPublishYear().toString().contains(stringToSearch))
                    .toList());
            results.addAll(bookList.stream()
                    .filter(book -> book.getResume().contains(stringToSearch))
                    .toList());

            updateTableView(results);
        }
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml",SceneLoader.getUsername() + "(Administrator)");
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
        anchorPane.requestFocus();
        checkAndUpdateButtons(mouseEvent);
    }

    public void resumeTextAreaButtonOnMouseClicked() {
        removeBookButton.setDisable(true);
        loadBooksButton.setDisable(true);
    }

    @FXML
    public void removeBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if(selectedBook !=null){
            adminService.removeBook(selectedBook);
            updateTableView(adminService.getBooks());
        }
        checkAndUpdateButtons(mouseEvent);
    }

    private void openDialog() {
        // Dialog window
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(SceneLoader.getStage());  // Set the owner window


        TextField quantityField = new TextField();
        Button increaseButton = new Button("Increase Quantity");

        increaseButton.setOnAction(e -> {

            Integer quantity= Integer.valueOf(quantityField.getText());
            if(quantity>0){
                Book book=booksTableView.getSelectionModel().getSelectedItem();
                book.setAmountOfCopies(book.getAmountOfCopies()+ quantity);
                ((AdminService)ServiceFactory.getService(AdminService.class)).saveBook(book);
                updateTableView(adminService.getBooks());
                dialogStage.close();
            }else{
               // ..
            }

        });

        VBox dialogLayout = new VBox(10, new Label("Enter quantity:"), quantityField, increaseButton);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 250, 150);

        dialogStage.setTitle("Quantity Dialog");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}
