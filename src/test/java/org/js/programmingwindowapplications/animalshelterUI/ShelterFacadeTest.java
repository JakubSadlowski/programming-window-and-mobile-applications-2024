package org.js.programmingwindowapplications.animalshelterUI;

import org.js.programmingwindowapplications.animalshelter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelterFacadeTest {

    private ShelterManager shelterManager;
    private AccountsManager accountsManager;
    private ShelterFacade shelterFacade;

    @BeforeEach
    void setUp() {
        shelterManager = mock(ShelterManager.class);
        accountsManager = mock(AccountsManager.class);
        shelterFacade = new ShelterFacade(shelterManager, accountsManager);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Given
        when(accountsManager.authenticate("user", "password")).thenReturn("TOKEN_123");

        // When
        String token = shelterFacade.login("user", "password");

        // Then
        assertEquals("TOKEN_123", token, "Login should return a valid token.");
        verify(accountsManager).authenticate("user", "password");
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() {
        // Given
        when(accountsManager.authenticate("user", "wrongPassword")).thenReturn(null);

        // When
        String token = shelterFacade.login("user", "wrongPassword");

        // Then
        assertNull(token, "Login should return null for invalid credentials.");
        verify(accountsManager).authenticate("user", "wrongPassword");
    }

    @Test
    void shouldReturnAllShelters() {
        // Given
        Map<String, AnimalShelter> shelters = Map.of("Shelter1", new AnimalShelter("Shelter1", 10, "123"));
        when(shelterManager.getShelters()).thenReturn(shelters);

        // When
        Map<String, AnimalShelter> result = shelterFacade.getShelters();

        // Then
        assertEquals(shelters, result, "Should return all shelters from ShelterManager.");
        verify(shelterManager).getShelters();
    }

    @Test
    void shouldReturnAnimalsFromSpecificShelter() {
        // Given
        List<Animal> animals = List.of(new Animal("Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0));
        when(shelterManager.getAnimalsFromShelter("Shelter1")).thenReturn(animals);

        // When
        List<Animal> result = shelterFacade.getAnimals("Shelter1");

        // Then
        assertEquals(animals, result, "Should return all animals from the specified shelter.");
        verify(shelterManager).getAnimalsFromShelter("Shelter1");
    }

    @Test
    void shouldAddAnimalToShelter() {
        // When
        shelterFacade.addAnimal("Shelter1", "Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0);

        // Then
        verify(shelterManager).addAnimal("Shelter1", "Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0);
    }

    @Test
    void shouldAddShelter() {
        // When
        shelterFacade.addShelter("NewShelter", 20, "123-456-789");

        // Then
        verify(shelterManager).addShelter("NewShelter", 20, "123-456-789");
    }

    @Test
    void shouldModifyShelter() {
        // When
        shelterFacade.modifyShelter("OldShelter", "NewShelter");

        // Then
        verify(shelterManager).modifyShelter("OldShelter", "NewShelter");
    }

    @Test
    void shouldModifyAnimalDetails() {
        // When
        shelterFacade.modifyAnimal("Shelter1", "Azor", "Buddy", "Dog", AnimalCondition.QUARANTINE, 5, 50.0);

        // Then
        verify(shelterManager).modifyAnimal("Shelter1", "Azor", "Buddy", "Dog", AnimalCondition.QUARANTINE, 5, 50.0);
    }

    @Test
    void shouldRemoveShelter() {
        // When
        shelterFacade.removeShelter("Shelter1");

        // Then
        verify(shelterManager).removeShelter("Shelter1");
    }

    @Test
    void shouldRemoveAnimalFromShelter() {
        // Given
        Animal animal = new Animal("Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0);
        AnimalShelter shelter = mock(AnimalShelter.class);
        when(shelterManager.getShelter("Shelter1")).thenReturn(shelter);

        // When
        shelterFacade.removeAnimal("Shelter1", animal);

        // Then
        verify(shelter).removeAnimal(animal);
    }

    @Test
    void shouldAdoptAnimalFromShelter() {
        // Given
        Animal animal = new Animal("Azor", "Dog", AnimalCondition.HEALTHY, 3, 40.0);
        AnimalShelter shelter = mock(AnimalShelter.class);
        when(shelterManager.getShelter("Shelter1")).thenReturn(shelter);
        when(shelter.adoptAnimal(animal)).thenReturn(animal);

        // When
        Animal adoptedAnimal = shelterFacade.adoptAnimal("Shelter1", animal);

        // Then
        assertEquals(animal, adoptedAnimal, "Adopted animal should be returned.");
        verify(shelter).adoptAnimal(animal);
    }

    @Test
    void shouldReturnShelterPhoneNumber() {
        // Given
        AnimalShelter shelter = mock(AnimalShelter.class);
        when(shelterManager.getShelter("Shelter1")).thenReturn(shelter);
        when(shelter.getPhoneNumber()).thenReturn("123-456-789");

        // When
        String phoneNumber = shelterFacade.getShelterPhoneNumber("Shelter1");

        // Then
        assertEquals("123-456-789", phoneNumber, "Phone number should match the expected value.");
        verify(shelter).getPhoneNumber();
    }
}
