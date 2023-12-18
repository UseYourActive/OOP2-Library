package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.tableViews.BookTreeItem;
import com.library.frontend.utils.tableViews.TableViewBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
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

    @FXML public TreeTableView<String> bookTreeTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> switchButton.requestFocus());
        populateTreeTableView();

    }

    private void populateTreeTableView(){
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);

        TableViewBuilder.buildBookTreeTableView(bookTreeTableView);

        AdminService service= (AdminService) ServiceFactory.getService(AdminService.class);
        for(Book book : service.getBooks())
        {
            BookTreeItem bookTreeItem=new BookTreeItem(book);
            root.getChildren().add(bookTreeItem);
        }
        bookTreeTableView.setRoot(root);
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

    }
    @FXML
    public void searchBookButtonOnMouseClicked() {
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml", SceneLoader.getStage().getTitle());
    }
}
