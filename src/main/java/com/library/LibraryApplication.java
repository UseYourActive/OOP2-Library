package com.library;

import com.library.frontend.utils.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneLoader.load(stage,"/views/base/logInScene.fxml", "Library");
    }

    public static void main(String[] args) {
        launch(args);
    }
}