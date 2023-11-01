module frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires backend;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires spring.context;

    opens com.library.controllers to javafx.fxml;
    exports com.library.controllers;
}