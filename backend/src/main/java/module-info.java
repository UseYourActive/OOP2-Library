module com.library.backend {
    requires jakarta.validation;
    requires com.library.database;
    requires spring.context;
    requires java.sql;
    requires bcrypt;
    requires org.slf4j;
    requires lombok;
    requires org.mapstruct;

    exports com.library.processors;
    exports com.library.mappers;
    exports com.library.operations;
    exports com.library.exceptions;
    exports com.library.annotations;
    exports com.library.requests;
    exports com.library.responses;
}