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
    requires org.hibernate.orm.core;
    // backend

    // database
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires spring.orm;
    requires spring.jdbc;
    requires spring.boot.autoconfigure;
    // database

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.naming;
    requires spring.boot;

    opens com.library.libraryproject.database.entities to org.hibernate.orm.core,
            spring.core;

    exports com.library.libraryproject.backend.processors to spring.beans;

    exports com.library.libraryproject.backend.operations;

    opens com.library.libraryproject.backend.config to spring.core,
            spring.beans,
            spring.context;

    exports com.library.libraryproject.backend.mappers to spring.beans;

    exports com.library.libraryproject.frontend.controllers;
    opens com.library.libraryproject.frontend.controllers to javafx.fxml, spring.core;

    exports com.library.libraryproject;
    opens com.library.libraryproject to javafx.fxml, spring.core;
}