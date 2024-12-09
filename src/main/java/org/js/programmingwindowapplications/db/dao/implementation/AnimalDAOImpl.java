package org.js.programmingwindowapplications.db.dao.implementation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.entities.AnimalEntity;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;

import java.util.List;
import java.util.Optional;

public class AnimalDAOImpl implements AnimalDAO {

    @Override
    public List<AnimalEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AnimalEntity", AnimalEntity.class).list();
        }
    }

    @Override
    public void save(AnimalEntity entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void update(AnimalEntity entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(AnimalEntity entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
