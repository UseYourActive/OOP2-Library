module com.library.oop2library {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires jakarta.validation;

    opens com.library.oop2library to javafx.fxml;
    exports com.library.oop2library;
    exports com.library.controllers;
    opens com.library.controllers to javafx.fxml;
}