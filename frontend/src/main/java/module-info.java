module frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires backend;

    opens com.library.controllers to javafx.fxml;
    exports com.library.controllers;
    opens com.library to javafx.fxml;
    exports com.library;
}