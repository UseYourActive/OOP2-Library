package com.library.backend.services.trying;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {
    Optional<T> findById(Long id);
    T getById(Long id);
    List<T> findAll();
    void delete(Long id);
    void deleteAll(Collection<T> collection);
    void update(T object);
    void updateAll(Collection<T> collection);
    void save(T object);
    void saveAll(Collection<T> collection);
}
