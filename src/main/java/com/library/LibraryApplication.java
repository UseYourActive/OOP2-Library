package com.library;

import com.library.frontend.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage){
        SceneLoader.load(stage, "/views/logInScene.fxml", "Library");
    }

    public static void main(String[] args) {
        launch(args);
    }
}