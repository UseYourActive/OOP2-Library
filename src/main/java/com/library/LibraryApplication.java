package com.library;

import com.library.frontend.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class representing the entry point for the Library Application.
 * Extends the JavaFX Application class and provides the start method to initialize the application's main stage.
 */
public class LibraryApplication extends Application {

    /**
     * The main entry point for the application.
     * Initializes the main stage by loading the login scene using the SceneLoader.
     *
     * @param stage The primary stage for the application, provided by the JavaFX platform.
     */
    @Override
    public void start(Stage stage) {
        // Load the login scene using the SceneLoader
        SceneLoader.load(stage, "/views/logInScene.fxml", "Library");
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
