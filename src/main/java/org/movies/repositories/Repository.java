package org.movies.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

    T save(T entity);
    T update(T entity);
    boolean deleteById(K id);
    Optional<T> findById(K id);
    List<T> findAll();
    long count();
    boolean existsById(K id);
}
