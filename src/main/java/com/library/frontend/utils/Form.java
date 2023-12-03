package com.library.frontend.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public final class Form {

    private final Stage stage;
    private final String resourceFiles;
    private final String stageTitle;

    public Form(ActionEvent event, String resourceFiles, String stageTitle) {
        this.resourceFiles = resourceFiles;
        this.stageTitle = stageTitle;
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public Form(Stage stage, String resourceFiles, String stageTitle) {
        this.stage = stage;
        this.resourceFiles = resourceFiles;
        this.stageTitle = stageTitle;
    }

    public void load() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resourceFiles));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }
}
