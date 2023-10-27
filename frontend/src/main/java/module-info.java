module com.library.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires jakarta.validation;
    requires spring.context;
    requires com.library.backend;

    opens com.library.oop2library to javafx.fxml;
    exports com.library.oop2library;

    opens com.library.controllers to javafx.fxml;
    exports com.library.controllers;
}
