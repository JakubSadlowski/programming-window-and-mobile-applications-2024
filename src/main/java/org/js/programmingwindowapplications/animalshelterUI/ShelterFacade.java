package org.js.programmingwindowapplications.animalshelterUI;

import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalShelterDAOImpl;

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
        shelterManager.getShelter(shelterName).removeAnimal(animal);
    }

    public Animal adoptAnimal(String shelterName, Animal animal) {
        return shelterManager.getShelter(shelterName).adoptAnimal(animal);
    }

    public String getShelterPhoneNumber(String shelterName) {
        return shelterManager.getShelter(shelterName).getPhoneNumber();
    }
}
