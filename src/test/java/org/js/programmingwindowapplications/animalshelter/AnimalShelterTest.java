package org.js.programmingwindowapplications.animalshelter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalShelterTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;
    private AnimalShelter animalShelter;
    private Animal animal1, animal2, animal3, animal4, animal5;

    @BeforeEach
    public void setUp() {
        animalShelter = new AnimalShelter("Shelter", 4, "555-222-111");
        animal1 = new Animal("Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0);
        animal2 = new Animal("Mittens", "Siamese", AnimalCondition.HEALTHY, 2, 80.0);
        animal3 = new Animal("Coco", "Parrot", AnimalCondition.ADOPTED, 5, 120.0);
        animal4 = new Animal("Rocky", "Ferret", AnimalCondition.QUARANTINE, 3, 200.0);

        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    void addAnimalTest() {
        //When
        boolean addedAnimal = animalShelter.addAnimal(animal1);

        //Then
        assertTrue(addedAnimal);
    }

    @Test
    void addOverMaxCapacityTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        boolean added = animalShelter.addAnimal(animal5);

        //Then
        assertFalse(added, "Adding an animal should return false when capacity is exceeded.");

        String expectedErrorMessage = "Shelter is at full capacity! Cannot add more animals.";
        assertTrue(errContent.toString().contains(expectedErrorMessage),
                "Expected error message should be printed.");

        System.setErr(System.err);
    }

    private void addAnimals(Animal... animals) {
        for (Animal animal : animals) {
            animalShelter.addAnimal(animal);
        }
    }

    @Test
    void removeAnimalTest() {
        //Given
        addAnimals(animal1);

        //When
        animalShelter.removeAnimal(animal1);

        //Then
        assertEquals(0, animalShelter.getCapacity());
    }

    @Test
    void adoptAnimalShouldReturnAnimalAndChangeConditionTest() {
        //Given
        addAnimals(animal1);

        // When
        Animal adoptedAnimal = animalShelter.adoptAnimal(animal1);

        // Then
        assertNotNull(adoptedAnimal, "Animal should be returned when it exists in the shelter.");
        assertEquals(AnimalCondition.ADOPTED, adoptedAnimal.getCondition(), "Animal condition should be changed to ADOPTED.");
        assertEquals(0, animalShelter.getCapacity(), "Animal should be removed from the shelter.");
    }

    @Test
    void adoptAnimalShouldReturnNullWhenAnimalDoesNotExistTest() {
        //Given
        addAnimals(animal1);

        // When
        Animal adoptedAnimal = animalShelter.adoptAnimal(animal2);

        // Then
        assertNull(adoptedAnimal, "Animal should be null when it does not exist in the shelter.");
        assertEquals(1, animalShelter.getCapacity(), "Shelter capacity should remain the same.");
    }

    @Test
    void sortByNameTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        List<Animal> sortedAnimals = animalShelter.sortByName();

        //Then
        assertEquals("Azor", sortedAnimals.get(0).getName());
        assertEquals("Coco", sortedAnimals.get(1).getName());
        assertEquals("Mittens", sortedAnimals.get(2).getName());
        assertEquals("Rocky", sortedAnimals.get(3).getName());
    }

    @Test
    void changeAgeWithAgeZeroTest() {
        //Given
        addAnimals(animal1);

        //When
        animalShelter.changeAge(animalShelter.getAnimalFromShelter(animal1), 0);

        //Then
        assertEquals(4, animalShelter.getAnimalFromShelter(animal1).getAge());
    }

    @Test
    void countByConditionHealthyTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        int conditionHealthyCounter = animalShelter.countByCondition(AnimalCondition.HEALTHY);

        //Then
        assertEquals(2, conditionHealthyCounter);
    }

    @Test
    void sortByPriceTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        List<Animal> sortedAnimals = animalShelter.sortByPrice();

        //Then
        assertEquals(40.0, sortedAnimals.get(0).getPrice());
        assertEquals(80.0, sortedAnimals.get(1).getPrice());
        assertEquals(120.0, sortedAnimals.get(2).getPrice());
        assertEquals(200.0, sortedAnimals.get(3).getPrice());
    }

    @Test
    void searchFoundTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        String result = animalShelter.search("Azor");

        //Then
        assertEquals("Name: Azor, Species: Dog, Age: 3, Price: 40.0", result);
    }

    @Test
    void searchNotFoundTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        String result = animalShelter.search("Unknown");

        //Then
        assertEquals("Animal with name Unknown not found.", result);
    }

    @Test
    void searchPartialFoundMatchTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        List<Animal> results = animalShelter.searchPartial("Co");

        //Then
        assertEquals(1, results.size());
        assertEquals("Coco", results.getFirst().getName());
    }

    @Test
    void searchPartialNoMatchTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        List<Animal> results = animalShelter.searchPartial("Tiger");

        //Then
        assertTrue(results.isEmpty());
    }

    @Test
    void maxTest() {
        //Given
        addAnimals(animal1, animal2, animal3, animal4);

        //When
        Animal maxPrice = animalShelter.max();

        //Then
        assertEquals(200.0, maxPrice.getPrice());
    }
}