package org.js.programmingwindowapplications.db.dao.implementation;

import org.hibernate.Session;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.entities.AnimalEntity;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public class AnimalDAOImpl extends GenericDAOImpl<AnimalEntity> implements AnimalDAO {

    public AnimalDAOImpl() {
        super(AnimalEntity.class);
    }

    @Override
    public List<AnimalEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AnimalEntity> query = cb.createQuery(AnimalEntity.class);
            Root<AnimalEntity> root = query.from(AnimalEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        }
    }
}