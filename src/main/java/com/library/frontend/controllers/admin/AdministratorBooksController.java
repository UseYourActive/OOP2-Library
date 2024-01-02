package com.library.frontend.controllers.admin;

import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.BookInventory;
import com.library.frontend.controllers.Controller;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import com.library.frontend.utils.engines.BookInventorySearchEngine;
import com.library.frontend.utils.engines.SearchEngine;
import com.library.frontend.utils.tableviews.InventoryTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorBooksController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorBooksController.class);

    @FXML public Button operatorsButton;
    @FXML public TextField searchBookTextField;
    @FXML public Button searchBookButton;
    @FXML public TextArea bookTextArea;
    @FXML public TableView<BookInventory> inventoryTableView;
    @FXML public AnchorPane anchorPane;
    @FXML public Button logOutButton;
    private AdminService adminService;
    private SearchEngine<BookInventory> searchEngine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminService = ServiceFactory.getService(AdminService.class);
        searchEngine = new BookInventorySearchEngine();

        operatorsButton.requestFocus();

        bookTextArea.setFocusTraversable(false);

        TableViewBuilder<BookInventory> bookInventoryTableViewBuilder = new InventoryTableViewBuilder();
        bookInventoryTableViewBuilder.createTableViewColumns(inventoryTableView);

        updateTableView(adminService.getAllBookInventories());

        prepareContextMenu();
    }

    @FXML
    public void searchBookButtonOnMouseClicked() {
        try {
            List<BookInventory> inventories = adminService.getAllBookInventories();
            String stringToSearch = searchBookTextField.getText();
            Collection<BookInventory> results = searchEngine.search(inventories, stringToSearch);
            updateTableView(results.stream().toList());
        } catch (Exception e) {
            logger.error("Error occurred during book search", e);
        }
    }

    @FXML
    public void operatorsButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            SceneLoader.load(mouseEvent, "/views/admin/administratorOperatorsScene.fxml", SceneLoader.getUsername() + "(Administrator)");
        } catch (Exception e) {
            logger.error("Error occurred during navigation to operators scene", e);
        }
    }

    @FXML
    public void booksTableViewOnClicked(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                BookInventory selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    SceneLoader.loadModalityDialog("/views/admin/administratorBooksDialogScene.fxml",selectedItem.getRepresentiveBook().getTitle(),selectedItem);
                    updateTableView(adminService.getAllBookInventories());
                }
            } else {
                BookInventory selectedInventory = inventoryTableView.getSelectionModel().getSelectedItem();

                if (selectedInventory != null)
                    bookTextArea.setText(selectedInventory.toString());
            }
        } catch (Exception e) {
            logger.error("Error occurred during handling book table view click", e);
        }
    }

    @FXML
    public void logOutButtonOnMouseClicked(MouseEvent mouseEvent) {
        try {
            SceneLoader.load(mouseEvent, "/views/logInScene.fxml", "LogIn");
        } catch (Exception e) {
            logger.error("Error occurred during logout", e);
        }
    }

    @FXML
    public void anchorPaneOnMouseClicked() {
        try {
            anchorPane.requestFocus();
            inventoryTableView.getSelectionModel().clearSelection();
        } catch (Exception e) {
            logger.error("Error occurred during handling anchor pane click", e);
        }
    }


    private void updateTableView(List<BookInventory> inventories) {
        try {
            inventoryTableView.getItems().clear();
            bookTextArea.clear();
            inventoryTableView.getItems().addAll(FXCollections.observableArrayList(inventories));
        } catch (Exception e) {
            logger.error("Error occurred during table view update", e);
        }
    }

    private void prepareContextMenu() {
        try {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem registerNewBook = new MenuItem("Register book");
            MenuItem addExistingBookItem = new MenuItem("Add books");
            MenuItem removeBookItem = new MenuItem("Remove inventory");

            contextMenu.getItems().addAll(registerNewBook, removeBookItem, addExistingBookItem);

            inventoryTableView.setContextMenu(contextMenu);

            registerNewBook.setOnAction(this::registerNewBook);
            removeBookItem.setOnAction(this::removeSelectedInventory);
            addExistingBookItem.setOnAction(this::setQuantityOnSelectedBook);
        } catch (Exception e) {
            logger.error("Error occurred during context menu preparation", e);
        }
    }

    private void registerNewBook(ActionEvent mouseEvent) {
        try {
            SceneLoader.load("/views/admin/registerNewBookScene.fxml", "Register new book");
        } catch (Exception e) {
            logger.error("Error occurred during navigation to register new book scene", e);
        }
    }



    private void removeSelectedInventory(ActionEvent actionEvent) {
        try {
            BookInventory inventory = inventoryTableView.getSelectionModel().getSelectedItem();

            if (inventory!=null) {
                if (DialogUtils.showConfirmation("Confirmation", "Are you sure you want to delete these book/s from the database ?")) {

                    adminService.removeInventory(inventory);

                    updateTableView(adminService.getAllBookInventories());
                }
            } else {
                DialogUtils.showInfo("Information", "Please select an inventory!");
            }
        } catch (Exception e) {
            logger.error("Error occurred during removing selected books", e);
        }
    }

    private void setQuantityOnSelectedBook(ActionEvent actionEvent) {
        try {
            if (!inventoryTableView.getSelectionModel().isEmpty()) {
                openDialog();
            } else {
                DialogUtils.showInfo("Information", "Please select a book!");
            }
        } catch (Exception e) {
            logger.error("Error occurred during setting quantity on selected book", e);
        }
    }

    private void openDialog() {
        try {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(SceneLoader.getStage());

            TextField quantityField = new TextField();
            Button increaseButton = new Button("Increase Quantity");

            increaseButton.setOnAction(e -> {
                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity > 0) {
                        BookInventory bookInventory = inventoryTableView.getSelectionModel().getSelectedItem();
                        //bookInventory.setQuantity(quantity+bookInventory.getQuantity());
                        adminService.saveInventory(bookInventory);

                        updateTableView(adminService.getAllBookInventories());
                        dialogStage.close();
                    } else {
                        DialogUtils.showInfo("Error", "Please enter valid quantity number!");
                    }
                } catch (Exception ex) {
                    logger.error("Error occurred during setting quantity on selected book", ex);
                }
            });

            VBox dialogLayout = new VBox(10, new Label("Enter quantity:"), quantityField, increaseButton);
            dialogLayout.setPadding(new Insets(20));

            Scene dialogScene = new Scene(dialogLayout, 250, 150);

            dialogStage.setTitle("Set quantity");
            dialogStage.setScene(dialogScene);
            dialogStage.show();
        } catch (Exception e) {
            logger.error("Error occurred during opening dialog", e);
        }
    }
}
