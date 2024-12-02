package org.js.programmingwindowapplications.db.dao;

import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;

import java.util.List;
import java.util.Optional;

public interface AnimalShelterDAO extends GenericDAO<AnimalShelterEntity> {
    Optional<AnimalShelterEntity> findByName(String name);
    List<AnimalShelterEntity> findEmpty();
}
