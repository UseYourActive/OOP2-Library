module com.library.libraryproject {
    // frontend
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    // frontend

    // backend
    requires jakarta.validation;
    requires spring.context;
    requires java.sql;
    requires bcrypt;
    requires org.slf4j;
    requires spring.core;
    requires lombok;
    requires java.logging;
    requires spring.beans;
    // backend

    // database
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires spring.orm;
    requires spring.jdbc;
    // database

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports com.library.libraryproject.frontend.controllers;
    opens com.library.libraryproject.frontend.controllers to javafx.fxml;
    exports com.library.libraryproject;
    opens com.library.libraryproject to javafx.fxml;
}