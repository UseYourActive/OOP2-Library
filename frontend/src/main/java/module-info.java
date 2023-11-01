module frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;

    opens com.library.controllers to javafx.fxml;
    exports com.library.controllers;
}