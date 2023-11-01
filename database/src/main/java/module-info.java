module database {
    requires lombok;
    requires jakarta.persistence;
    requires spring.data.jpa;

    opens com.library.entities;
    opens com.library.repositories;

    exports com.library.entities;
    exports com.library.repositories;
}