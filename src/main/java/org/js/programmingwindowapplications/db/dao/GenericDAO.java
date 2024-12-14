package org.js.programmingwindowapplications.db.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
