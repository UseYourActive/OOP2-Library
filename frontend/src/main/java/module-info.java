module com.library.oop2library {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.library.oop2library to javafx.fxml;
    exports com.library.oop2library;
}