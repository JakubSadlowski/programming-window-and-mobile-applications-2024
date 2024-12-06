package org.js.programmingwindowapplications.db.dao;

import org.js.programmingwindowapplications.db.entities.AnimalEntity;
import java.util.List;
import java.util.Optional;

public interface AnimalDAO extends GenericDAO<AnimalEntity> {
    List<AnimalEntity> findByShelter(Long shelterId);
    Optional<AnimalEntity> findByNameAndShelterId(String name, Long shelterId);
}
