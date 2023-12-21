package com.library.frontend.utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private SceneLoader(){}
    public static void load(Event event, String resourceFiles, String stageTitle){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loadBackground(stage,resourceFiles,stageTitle);
    }

    public static void load(Stage stage,String resourceFiles, String stageTitle){
        loadBackground(stage,resourceFiles,stageTitle);
    }

    private static void loadBackground(Stage stage, String resourceFiles, String stageTitle){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneLoader.class.getResource(resourceFiles)));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(stageTitle);
            stage.show();
            SceneLoader.stage=stage;

        } catch (IOException e) {
            logger.error("Error loading form: {}", resourceFiles, e);
            DialogUtils.showError("Error", "An error occurred when trying to open the new dialog window!", e.getMessage());
        }
    }

}
