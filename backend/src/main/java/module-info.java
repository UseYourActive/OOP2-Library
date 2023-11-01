module backend {
    requires jakarta.validation;
    requires database;
    requires spring.context;
    requires java.sql;
    requires bcrypt;
    requires org.slf4j;
    requires spring.core;
    requires lombok;
    requires java.logging;

    opens com.library.processors;
    opens com.library.mappers;
    opens com.library.operations;
    opens com.library.exceptions;
    opens com.library.annotations;
    opens com.library.requests;
    opens com.library.responses;

    exports com.library.processors;
    exports com.library.mappers;
    exports com.library.operations;
    exports com.library.exceptions;
    exports com.library.annotations;
    exports com.library.requests;
    exports com.library.responses;
}