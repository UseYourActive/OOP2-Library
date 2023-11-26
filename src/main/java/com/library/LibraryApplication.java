package com.library;


import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.OperationProcessorFactory;
import com.library.backend.operations.processors.contracts.CreateUserOperation;
import com.library.backend.operations.processors.CreateUserOperationProcessor;
import com.library.database.repositories.UserRepository;
import com.library.frontend.controllers.AccessController;
import com.library.frontend.controllers.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        OperationProcessorFactory operationProcessorFactory=new OperationProcessorFactory();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com.library/views/LogInForm.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        AccessController controller = fxmlLoader.getController();
        controller.setLogInOperation(operationProcessorFactory.getLogInOperation());

        stage.setResizable(false);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}