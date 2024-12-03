package org.js.programmingwindowapplications.animalshelter;

import jakarta.persistence.EntityManager;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.dao.RatingDAO;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalShelterDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.RatingDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShelterManagerTest {

    private ShelterManager shelterManager;
    private AnimalShelter shelter1;
    private AnimalShelter shelter2;
    private AnimalShelterDAO shelterDAO;
    private AnimalDAO animalDAO;
    private RatingDAO ratingDAO;
    EntityManager entityManager = HibernateUtil.getEntityManager();

    @BeforeEach
    public void setUp() {
        shelterDAO = new AnimalShelterDAOImpl();
        animalDAO = new AnimalDAOImpl();
        ratingDAO = new RatingDAOImpl(entityManager);
        shelterManager = new ShelterManager(animalDAO, shelterDAO, ratingDAO);
        shelter1 = new AnimalShelter("Shelter1", 5, "555-111-222");
        shelter2 = new AnimalShelter("Shelter2", 10, "555-333-444");

        shelterManager.addShelter(shelter1.getShelterName(), shelter1.getMaxCapacity(), shelter1.getPhoneNumber());
        shelterManager.addShelter(shelter2.getShelterName(), shelter2.getMaxCapacity(), shelter2.getPhoneNumber());
    }

    @Test
    void getShelterTest() {
        // Given
        String shelterName = "Shelter1";

        // When
        AnimalShelter result = shelterManager.getShelter(shelterName);

        // Then
        assertNotNull(result, "The shelter should be found.");
        assertEquals(shelterName, result.getShelterName(), "Shelter name should match the requested one.");
    }

    @Test
    void addShelterTest() {
        // Given
        String newShelterName = "Shelter3";
        String phoneNumber = "555-555-555";
        int capacity = 8;

        // When
        boolean added = shelterManager.addShelter(newShelterName, capacity, phoneNumber);

        // Then
        assertTrue(added, "The shelter should be added successfully.");
        assertNotNull(shelterManager.getShelter(newShelterName), "The new shelter should be retrievable.");
    }

    @Test
    void addShelterWithExistingNameTest() {
        // Given
        String existingShelterName = "Shelter1"; // Already exists

        // When
        boolean added = shelterManager.addShelter(existingShelterName, 6, "555-999-000");

        // Then
        assertFalse(added, "Shelter with the same name should not be added.");
    }

    /*@Test
    void removeShelterTest() {
        // Given
        String shelterName = "Shelter1";

        // When
        AnimalShelter removedShelter = shelterManager.removeShelter(shelterName);

        // Then
        assertNotNull(removedShelter, "The shelter should be removed.");
        assertNull(shelterManager.getShelter(shelterName), "The shelter should no longer be retrievable.");
    }

    @Test
    void removeNonExistingShelterTest() {
        // Given
        String nonExistingShelterName = "NonExistentShelter";

        // When
        AnimalShelter removedShelter = shelterManager.removeShelter(nonExistingShelterName);

        // Then
        assertNull(removedShelter, "Removing a non-existing shelter should return null.");
    }*/

    @Test
    void findEmptyTest() {
        // Given
        AnimalShelter shelter3 = new AnimalShelter("Shelter3", 5, "555-444-555");
        shelterManager.addShelter(shelter3.getShelterName(), shelter3.getMaxCapacity(), shelter3.getPhoneNumber());
        shelterManager.getShelter(shelter3.getShelterName()).addAnimal(new Animal("Tommy", "Dog", AnimalCondition.HEALTHY, 3, 50.0));
        shelterManager.getShelter(shelter3.getShelterName()).addAnimal(new Animal("Bobby", "Cat", AnimalCondition.HEALTHY, 2, 60.0));

        // When
        List<AnimalShelter> emptyShelters = shelterManager.findEmpty();

        // Then
        assertFalse(emptyShelters.contains(shelter3), "Shelter3 should not be found because it has animals.");
        assertTrue(emptyShelters.contains(shelter2), "Shelter2 should be found because it is empty.");
        assertTrue(emptyShelters.contains(shelter1), "Shelter1 should be found because it is empty.");
    }

    @Test
    void modifyShelterNameTest() {
        // Given
        String oldName = "Shelter1";
        String newName = "Shelter1Modified";

        // When
        shelterManager.modifyShelter(oldName, newName);

        // Then
        assertNull(shelterManager.getShelter(oldName), "The old shelter name should no longer exist.");
        assertNotNull(shelterManager.getShelter(newName), "The new shelter name should exist.");
    }

    @Test
    void modifyShelterNameToExistingNameTest() {
        // Given
        String oldName = "Shelter1";
        String existingName = "Shelter2";

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> shelterManager.modifyShelter(oldName, existingName),
                "Modifying shelter name to an existing name should throw an exception.");
    }

    @Test
    void modifyAnimalTest() {
        // Given
        shelterManager.addAnimal("Shelter1", "Azor", "Dog", AnimalCondition.HEALTHY, 3, 50.0);
        String oldAnimalName = "Azor";
        String newAnimalName = "AzorModified";
        String newSpecies = "Cat";
        AnimalCondition newCondition = AnimalCondition.ADOPTED;
        int newAge = 4;
        double newPrice = 55.0;

        // When
        shelterManager.modifyAnimal("Shelter1", oldAnimalName, newAnimalName, newSpecies, newCondition, newAge, newPrice);

        // Then
        Animal modifiedAnimal = shelterManager.getShelter("Shelter1").getAnimalList().stream()
                .filter(a -> a.getName().equals(newAnimalName))
                .findFirst()
                .orElse(null);

        assertNotNull(modifiedAnimal, "The animal should be modified.");
        assertEquals(newAnimalName, modifiedAnimal.getName(), "Animal name should be updated.");
        assertEquals(newSpecies, modifiedAnimal.getSpecies(), "Animal species should be updated.");
        assertEquals(newCondition, modifiedAnimal.getCondition(), "Animal condition should be updated.");
        assertEquals(newAge, modifiedAnimal.getAge(), "Animal age should be updated.");
        assertEquals(newPrice, modifiedAnimal.getPrice(), "Animal price should be updated.");
    }

    @Test
    void modifyNonExistingAnimalTest() {
        // Given
        String nonExistingAnimal = "NonExistentAnimal";

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> shelterManager.modifyAnimal("Shelter1", nonExistingAnimal, "NewName", "NewSpecies", AnimalCondition.HEALTHY, 3, 30.0),
                "Modifying a non-existing animal should throw an exception.");
    }

    @Test
    void summaryTest() {
        // Given
        shelterManager.addShelter("Shelter3", 10, "555-777-888");

        // When
        shelterManager.summary();
    }
}
