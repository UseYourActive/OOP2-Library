package com.library;

import com.library.frontend.utils.SceneLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneLoader.load(stage,"/views/logInScene.fxml", "Library");

        //Form form = new Form(stage, "/views/logInScene.fxml", "Library");
        //form.load();

        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/logInScene.fxml")));
        //Scene scene=new Scene(root);
        //stage.setScene(scene);
        //stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}