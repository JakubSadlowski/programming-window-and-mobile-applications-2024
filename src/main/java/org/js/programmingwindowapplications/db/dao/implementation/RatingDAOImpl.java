package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.js.programmingwindowapplications.db.dao.RatingDAO;
import org.js.programmingwindowapplications.db.entities.RatingEntity;
import java.util.List;

public class RatingDAOImpl extends GenericDAOImpl<RatingEntity> implements RatingDAO {

    public RatingDAOImpl() {
        super(RatingEntity.class);
    }

    @Override
    public List<RatingEntity> findByShelter(Long shelterId) {
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RatingEntity> query = cb.createQuery(RatingEntity.class);
            Root<RatingEntity> rating = query.from(RatingEntity.class);

            query.select(rating)
                    .where(cb.equal(rating.get("shelter").get("id"), shelterId))
                    .orderBy(cb.desc(rating.get("dateTime")));

            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public double getAverageRating(Long shelterId) {
        try (Session session = getSession()) {
            Double average = session.createQuery(
                            "SELECT AVG(r.value) FROM RatingEntity r WHERE r.shelter.id = :shelterId",
                            Double.class)
                    .setParameter("shelterId", shelterId)
                    .uniqueResult();
            return average != null ? average : 0.0;
        }
    }

    @Override
    public long getRatingCount(Long shelterId) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT COUNT(r) FROM RatingEntity r WHERE r.shelter.id = :shelterId",
                            Long.class)
                    .setParameter("shelterId", shelterId)
                    .uniqueResult();
        }
    }
}