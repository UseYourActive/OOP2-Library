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

    private final Boolean isResizable;

    public Form(ActionEvent event, String resourceFiles, String stageTitle, Boolean isResizable) {
        this.resourceFiles = resourceFiles;
        this.stageTitle = stageTitle;
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.isResizable = isResizable;
    }

    public Form(Stage stage, String resourceFiles, String stageTitle, Boolean isResizable) {
        this.stage = stage;
        this.resourceFiles = resourceFiles;
        this.stageTitle = stageTitle;
        this.isResizable = isResizable;
    }

    public void load() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resourceFiles));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setResizable(isResizable);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }
}
