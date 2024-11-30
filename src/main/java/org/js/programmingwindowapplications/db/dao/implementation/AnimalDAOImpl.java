package org.js.programmingwindowapplications.db.dao.implementation;

import jakarta.persistence.EntityManager;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.entities.AnimalEntity;

public class AnimalDAOImpl extends GenericDAOImpl<AnimalEntity> implements AnimalDAO {
    public AnimalDAOImpl(EntityManager entityManager) {
        super(entityManager, AnimalEntity.class);
    }
}
