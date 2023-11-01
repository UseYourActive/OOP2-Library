module database {
    requires lombok;
    requires jakarta.persistence;
    requires spring.data.jpa;

    exports com.library.entities;
    exports com.library.repositories;
}