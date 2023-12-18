package com.library.frontend.controllers;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Book;
import com.library.frontend.utils.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
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
    @FXML public TreeView<Book> bookTreeView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> switchButton.requestFocus());
        AdminService service= (AdminService) ServiceFactory.getService(AdminService.class);
        //usersListView.setItems(FXCollections.observableArrayList(service.getUsers()));
    }

    @FXML
    public void createOperatorButtonOnAction(ActionEvent event) {
        SceneLoader.load(event,"/views/createOperatorScene.fxml","Create operator");
    }

    @FXML
    public void removeOperatorButtonOnAction() {
        AdminService adminService = (AdminService) ServiceFactory.getService(AdminService.class);
       // adminService.removeOperator(usersListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void archiveBookButtonOnAction(ActionEvent event) {
        AdminService adminService = (AdminService) ServiceFactory.getService(AdminService.class);
       // adminService.archiveBook(booksListView.getSelectionModel().getSelectedItem());
    }



    @FXML
    public void registerBookButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/registerNewBookScene.fxml","Register new book");
    }

    public void loadBooksButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void scrapBookButtonOnMouseClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void searchBookButtonOnMouseClicked(MouseEvent mouseEvent) {
    }


    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        SceneLoader.load(mouseEvent,"/views/administratorOperatorsScene.fxml","Administrator");
    }
}
