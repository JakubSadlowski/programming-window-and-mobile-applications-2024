package org.js.programmingwindowapplications.db.dao.implementation;

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
            return session.createQuery(
                            "FROM RatingEntity WHERE shelter.id = :shelterId ORDER BY dateTime DESC",
                            RatingEntity.class)
                    .setParameter("shelterId", shelterId)
                    .list();
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