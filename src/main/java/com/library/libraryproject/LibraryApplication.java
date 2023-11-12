package com.library.libraryproject;

import com.library.libraryproject.backend.config.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.IOException;

//@Import(AppConfig.class)
@SpringBootApplication
public class LibraryApplication extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);

//        context = new SpringApplicationBuilder(LibraryApplication.class)
//                .headless(false)
//                .run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("/com/library/libraryproject/views/LogInForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        fxmlLoader.setControllerFactory(context::getBean);
        stage.setResizable(false);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop(){
        context.close();
    }
}