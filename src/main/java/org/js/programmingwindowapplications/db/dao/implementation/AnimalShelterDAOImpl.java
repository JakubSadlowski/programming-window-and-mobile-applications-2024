package org.js.programmingwindowapplications.db.dao.implementation;

import org.hibernate.Session;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class AnimalShelterDAOImpl extends GenericDAOImpl<AnimalShelterEntity> implements AnimalShelterDAO {

    public AnimalShelterDAOImpl() {
        super(AnimalShelterEntity.class);
    }

    @Override
    public List<AnimalShelterEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AnimalShelterEntity> query = cb.createQuery(AnimalShelterEntity.class);
            Root<AnimalShelterEntity> root = query.from(AnimalShelterEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all shelters", e);
        }
    }

    @Override
    public Optional<AnimalShelterEntity> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AnimalShelterEntity> query = cb.createQuery(AnimalShelterEntity.class);
            Root<AnimalShelterEntity> root = query.from(AnimalShelterEntity.class);

            query.select(root)
                    .where(cb.equal(root.get("shelterName"), name));

            return session.createQuery(query)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error finding shelter by name: " + name, e);
        }
    }

    @Override
    public List<AnimalShelterEntity> findEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AnimalShelterEntity> query = cb.createQuery(AnimalShelterEntity.class);
            Root<AnimalShelterEntity> root = query.from(AnimalShelterEntity.class);

            query.select(root)
                    .where(cb.isEmpty(root.get("animals")));

            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding empty shelters", e);
        }
    }
}