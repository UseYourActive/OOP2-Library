package com.library;

import com.library.frontend.utils.Form;
import javafx.application.Application;
import javafx.stage.Stage;


public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Form form = new Form(stage,"/com.library/views/LogInForm.fxml","Library");
        form.load();
    }
    public static void main(String[] args) {
        launch(args);
    }

}