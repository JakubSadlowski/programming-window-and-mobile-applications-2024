package org.js.programmingwindowapplications.animalshelterUI;

import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;

import java.util.List;
import java.util.Map;

public class ShelterFacade {
    private final AccountsManager accountsManager;
    private final ShelterManager shelterManager;

    public ShelterFacade(ShelterManager shelterManager, AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
        this.shelterManager = shelterManager;
    }

    public String login(String username, String password) {
        return accountsManager.authenticate(username, password);
    }

    public Map<String, AnimalShelter> getShelters() {
        return shelterManager.getShelters();
    }

    public List<Animal> getAnimals(String shelterName) {
        return shelterManager.getAnimalsFromShelter(shelterName);
    }

    public void addAnimal(String shelterName, String name, String species, AnimalCondition condition, int age, double price) {
        shelterManager.addAnimal(shelterName, name, species, condition, age, price);
    }

    public void addShelter(String name, int maxCapacity, String phoneNumber) {
        shelterManager.addShelter(name, maxCapacity, phoneNumber);
    }

    public void modifyShelter(String oldName, String newName) {
        shelterManager.modifyShelter(oldName, newName);
    }

    public void modifyAnimal(String shelterName, String oldName, String newName, String newSpecies, AnimalCondition newCondition, int newAge, double newPrice) {
        shelterManager.modifyAnimal(shelterName, oldName, newName, newSpecies, newCondition, newAge, newPrice);
    }

    public void removeShelter(String shelterName) {
        shelterManager.removeShelter(shelterName);
    }

    public void removeAnimal(String shelterName, Animal animal) {
        shelterManager.removeAnimal(shelterName, animal);
    }

    public Animal adoptAnimal(String shelterName, Animal animal) {
        return shelterManager.adoptAnimal(shelterName, animal);
    }

    public String getShelterPhoneNumber(String shelterName) {
        return shelterManager.getShelter(shelterName).getPhoneNumber();
    }

    public void addRating(String shelterName, int value, String comment) {
        shelterManager.addRating(shelterName, value, comment);
    }

    public Map<String, Object> getShelterWithRatings(String shelterName) {
        return shelterManager.getShelterWithRatings(shelterName);
    }

    public void exportShelterToCSV(String shelterName, String filename) {
        shelterManager.exportShelterToCSV(shelterName, filename);
    }

    public void importShelterFromCSV(String shelterName, String filename) {
        shelterManager.importShelterFromCSV(shelterName, filename);
    }
}
