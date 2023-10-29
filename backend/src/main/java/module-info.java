module com.library.backend {
    requires jakarta.validation;
    requires com.library.database;
    requires spring.context;
    requires java.sql;
    requires bcrypt;
    requires org.slf4j;
    requires lombok;
    requires org.mapstruct;

    exports com.library.processor;
    exports com.library.mapper;
    exports com.library.operation;
    exports com.library.exception;
    exports com.library.annotations;
    exports com.library.requests;
    exports com.library.responses;
}