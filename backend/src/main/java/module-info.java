module com.library.backend {
    requires lombok;
    requires jakarta.validation;
    requires com.library.database;
    requires org.mapstruct;
    requires spring.context;
    requires java.sql;
    requires bcrypt;
    requires org.slf4j;

    exports com.library.processors;
    exports com.library.requests;
    exports com.library.responses;
}