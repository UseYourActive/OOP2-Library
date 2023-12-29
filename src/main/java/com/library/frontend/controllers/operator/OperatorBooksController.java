package com.library.frontend.controllers.operator;

import com.library.backend.services.OperatorService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.base.Controller;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableviews.BookTreeTableViewBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OperatorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksController.class);
    @FXML public Button readersButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    @FXML public TreeTableView<Book> bookTreeTableView;
    @FXML public ListView<Book> selectedBooksListView;
    @FXML public Button lendButton;
    @FXML public Button lendReadingRoomButton;

    private OperatorService operatorService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operatorService = (OperatorService) ServiceFactory.getService(OperatorService.class);

        //bookTreeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        BookTreeTableViewBuilder bookTreeTableViewBuilder=new BookTreeTableViewBuilder();
        bookTreeTableViewBuilder.createTreeTableViewColumns(bookTreeTableView);

        updateTreeTableView(operatorService.getAllBookInventories());

        selectedBooksListView.setTooltip(new Tooltip("Selected books will show here"));

        readersButton.requestFocus();


        prepareContextMenu();
    }

    @FXML
    public void readersButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SceneLoader.load(mouseEvent, "/views/operator/operatorReadersScene.fxml", "Readers");
        }
    }

    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                List<BookInventory> results = new ArrayList<>();


                String stringToSearch = searchBookTextField.getText();
                if (stringToSearch.isEmpty()) {
                    updateTreeTableView(operatorService.getAllBookInventories());

                } else {

                    for(BookInventory bookInventory: operatorService.getAllBookInventories()){

                        Book book=bookInventory.getRepresentiveBook();

                        if (book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(bookInventory);

                        if (book.getAuthor().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(bookInventory);

                        if (book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(bookInventory);

                        if (book.getGenre().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                            results.add(bookInventory);

                        if (book.getPublishYear() != null && book.getPublishYear().toString().contains(stringToSearch))
                            results.add(bookInventory);
                    }

                    updateTreeTableView(results);
                }
            } catch (Exception e) {
                logger.error("Error occurred during searching books", e);
            }
        }
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/base/logInScene.fxml","Log In");
    }
    @FXML
    public void anchorPaneOnMouseClicked() {
        bookTreeTableView.getSelectionModel().clearSelection();
    }

    @FXML
    public void bookTreeTableViewOnMouseClicked(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getClickCount() == 2
                    && mouseEvent.getButton() == MouseButton.PRIMARY
                    &&!bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {

                SceneLoader.loadModalityDialog("/views/operator/archiveBookScene.fxml","Archive Book");
                updateTreeTableView(operatorService.getAllBookInventories());
            } else {
               //if(!bookTreeTableView.getSelectionModel().isEmpty()&&bookTreeTableView.getSelectionModel().getSelectedItem().isLeaf()) {
               //    bookTextArea.setText(bookTreeTableView.getSelectionModel().getSelectedItem().getValue().toString());
               //}


                //for (TreeItem<Book> bookTreeItem : bookTreeTableView.getSelectionModel().getSelectedItems()) {
                //    if (!bookTreeItem.isLeaf()) {
                //        //bookTreeTableView.getSelectionModel().clearSelection();
                //        break;
                //    }
                //}
            }
        } catch (Exception e) {
            logger.error("Error occurred during handling book table view click", e);
        }
    }

    @FXML
    public void selectedBooksListViewOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void lendButtonOnMouseClicked() {
        SceneLoader.loadModalityDialog("/views/operator/createLendingBookFormScene.fxml","Create Book form");
    }

    @FXML
    public void lendReadingRoomButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem resume = new MenuItem("Register book");

            contextMenu.getItems().add(resume);

            bookTreeTableView.setContextMenu(contextMenu);

            resume.setOnAction(this::showResume);

        } catch (Exception e) {
            logger.error("Error occurred during context menu preparation", e);
        }
    }

    private void showResume(ActionEvent actionEvent){
        SceneLoader.loadModalityDialog("/views/operator/resumeShowScene.fxml","Resume");
    }


    private void updateTreeTableView(List<BookInventory> bookInventories){
        try {
            //Creating the parents
            bookTreeTableView.getRoot().getChildren().clear();
            for(BookInventory bookInventory: bookInventories){

                Book parentBook=Book.builder()
                        .id(bookInventory.getRepresentiveBook().getId())
                        .title(bookInventory.getRepresentiveBook().getTitle()+" "+bookInventory.getRepresentiveBook().getAuthor())
                        .build();

                TreeItem<Book> parent =new TreeItem<>(parentBook);

                //Creating the children
                for(Book book:bookInventory.getBookList()){

                    TreeItem<Book> child=new TreeItem<>(book);

                    parent.getChildren().add(child);//Adding child to the root element
                }

                bookTreeTableView.getRoot().getChildren().add(parent);
            }
        } catch (Exception e) {
            logger.error("Error occurred during updating book tree table view", e);
        }
    }

}
