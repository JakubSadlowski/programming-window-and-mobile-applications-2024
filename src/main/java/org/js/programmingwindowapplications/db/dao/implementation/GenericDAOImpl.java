package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.js.programmingwindowapplications.db.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

public class GenericDAOImpl<T> implements GenericDAO<T> {
    private final EntityManager entityManager;
    private final Class<T> entityClass;

    public GenericDAOImpl(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    @Override
    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
