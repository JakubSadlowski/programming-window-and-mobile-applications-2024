package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.EntityManager;
import org.js.programmingwindowapplications.db.dao.RatingDAO;
import org.js.programmingwindowapplications.db.entities.RatingEntity;
import java.util.List;

public class RatingDAOImpl extends GenericDAOImpl<RatingEntity> implements RatingDAO {

    public RatingDAOImpl(EntityManager entityManager) {
        super(entityManager, RatingEntity.class);
    }

    @Override
    public List<RatingEntity> findByShelter(Long shelterId) {
        return entityManager.createQuery(
                        "FROM RatingEntity WHERE shelter.id = :shelterId ORDER BY dateTime DESC",
                        RatingEntity.class)
                .setParameter("shelterId", shelterId)
                .getResultList();
    }

    @Override
    public double getAverageRating(Long shelterId) {
        Double average = entityManager.createQuery(
                        "SELECT AVG(r.value) FROM RatingEntity r WHERE r.shelter.id = :shelterId",
                        Double.class)
                .setParameter("shelterId", shelterId)
                .getSingleResult();
        return average != null ? average : 0.0;
    }

    @Override
    public long getRatingCount(Long shelterId) {
        return entityManager.createQuery(
                        "SELECT COUNT(r) FROM RatingEntity r WHERE r.shelter.id = :shelterId",
                        Long.class)
                .setParameter("shelterId", shelterId)
                .getSingleResult();
    }
}