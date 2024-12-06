package org.js.programmingwindowapplications.db.dao.implementation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;

import java.util.List;
import java.util.Optional;

public class AnimalShelterDAOImpl implements AnimalShelterDAO {

    @Override
    public Optional<AnimalShelterEntity> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(AnimalShelterEntity.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Error finding shelter by ID: " + id, e);
        }
    }

    @Override
    public List<AnimalShelterEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM AnimalShelterEntity", AnimalShelterEntity.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all shelters", e);
        }
    }

    @Override
    public Optional<AnimalShelterEntity> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM AnimalShelterEntity WHERE shelterName = :name",
                            AnimalShelterEntity.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error finding shelter by name: " + name, e);
        }
    }

    @Override
    public List<AnimalShelterEntity> findEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM AnimalShelterEntity s WHERE size(s.animals) = 0",
                            AnimalShelterEntity.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding empty shelters", e);
        }
    }

    @Override
    public void save(AnimalShelterEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving shelter", e);
        }
    }

    @Override
    public void update(AnimalShelterEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating shelter", e);
        }
    }

    @Override
    public void delete(AnimalShelterEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting shelter", e);
        }
    }
}