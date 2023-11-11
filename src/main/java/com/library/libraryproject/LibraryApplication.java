package com.library.libraryproject;

import com.library.libraryproject.backend.config.JpaConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Import(JpaConfig.class)
public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(JpaConfig.class);
//        context.refresh();

        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("/com/library/libraryproject/views/LogInForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}