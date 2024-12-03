package org.js.programmingwindowapplications.db.dao;

import org.js.programmingwindowapplications.db.entities.RatingEntity;
import java.util.List;

public interface RatingDAO extends GenericDAO<RatingEntity> {
    @Override
    void save(RatingEntity rating);
    List<RatingEntity> findByShelter(Long shelterId);
    double getAverageRating(Long shelterId);
}
