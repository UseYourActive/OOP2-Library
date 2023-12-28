package com.library.frontend.utils;

import com.library.database.entities.Book;
import com.library.frontend.utils.tableviews.BookTableViewBuilder;
import com.library.frontend.utils.tableviews.TableViewBuilder;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class SceneLoader {
    private static final Logger logger = LoggerFactory.getLogger(SceneLoader.class);

    @Getter
    @Setter
    private static String username;

    @Getter
    @Setter
    private static Stage stage;

    @Setter
    @Getter
    private static Object[] transferableObjects;

    private SceneLoader() {
    }

    public static void load(Event event, String resourceFiles, String stageTitle) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loadBackground(stage, resourceFiles, stageTitle);
    }

    public static void load(String resourceFiles, String stageTitle){
        if(stage==null)
            throw new RuntimeException();

        loadBackground(stage,resourceFiles,stageTitle);
    }

    public static void load(Stage stage, String resourceFiles, String stageTitle) {
        loadBackground(stage, resourceFiles, stageTitle);
    }

    private static void loadBackground(Stage stage, String resourceFiles, String stageTitle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneLoader.class.getResource(resourceFiles)));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(stageTitle);
            stage.show();
            SceneLoader.stage = stage;

            logger.info("Loaded scene: {}", resourceFiles);
        } catch (IOException e) {
            logger.error("Error loading form: {}", resourceFiles, e);
            DialogUtils.showError("Error", "An error occurred when trying to open the new dialog window!");
        }
    }

    public static void loadModalityDialog(String resourceFiles, String stageTitle,Object ... objects){
        try {
            SceneLoader.transferableObjects =objects;

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(SceneLoader.getStage());

            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneLoader.class.getResource(resourceFiles)));
            Scene dialogScene = new Scene(root);

            dialogStage.setTitle(stageTitle);
            dialogStage.setScene(dialogScene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (Exception e) {
            logger.error("Error occurred during opening dialog with table view", e);
        }
    }
}
