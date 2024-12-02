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
    public Optional<AnimalEntity> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(AnimalEntity.class, id));
        }
    }

    @Override
    public List<AnimalEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AnimalEntity", AnimalEntity.class).list();
        }
    }

    @Override
    public List<AnimalEntity> findByShelter(Long shelterId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from AnimalEntity where shelter.id = :shelterId",
                            AnimalEntity.class
                    )
                    .setParameter("shelterId", shelterId)
                    .list();
        }
    }

    @Override
    public Optional<AnimalEntity> findByNameAndShelterId(String name, Long shelterId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from AnimalEntity where name = :name and shelter.id = :shelterId",
                            AnimalEntity.class
                    )
                    .setParameter("name", name)
                    .setParameter("shelterId", shelterId)
                    .uniqueResultOptional();
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
