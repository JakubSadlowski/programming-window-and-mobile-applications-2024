package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.criteria.*;
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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Double> query = cb.createQuery(Double.class);
            Root<RatingEntity> rating = query.from(RatingEntity.class);

            query.select(cb.avg(rating.get("value")))
                    .where(cb.equal(rating.get("shelter").get("id"), shelterId));

            Double average = session.createQuery(query).uniqueResult();
            return average != null ? average : 0.0;
        }
    }

    @Override
    public long getRatingCount(Long shelterId) {
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<RatingEntity> rating = query.from(RatingEntity.class);

            query.select(cb.count(rating))
                    .where(cb.equal(rating.get("shelter").get("id"), shelterId));

            return session.createQuery(query).uniqueResult();
        }
    }
}