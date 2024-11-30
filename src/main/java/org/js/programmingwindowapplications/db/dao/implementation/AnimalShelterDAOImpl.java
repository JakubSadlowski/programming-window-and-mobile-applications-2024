package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.EntityManager;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;

public class AnimalShelterDAOImpl extends GenericDAOImpl<AnimalShelterEntity> implements AnimalShelterDAO {
    public AnimalShelterDAOImpl(EntityManager entityManager) {
        super(entityManager, AnimalShelterEntity.class);
    }
}
