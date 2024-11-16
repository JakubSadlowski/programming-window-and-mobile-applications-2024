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

    public String validateUser(String username, String password) {
        Account account = accountsManager.findAccount(username, password);
        if (account instanceof AdminAccount) {
            return "admin";
        } else if (account instanceof ClientAccount) {
            return "user";
        }
        return null;
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

    public void addShelter(String name, int maxCapacity) {
        shelterManager.addShelter(name, maxCapacity);
    }

    public void modifyShelter(String oldName, String newName) {
        if (!shelterManager.getShelters().containsKey(oldName)) {
            throw new IllegalArgumentException("Shelter with the name " + oldName + " does not exist.");
        }
        if (shelterManager.getShelters().containsKey(newName)) {
            throw new IllegalArgumentException("A shelter with the name " + newName + " already exists.");
        }

        AnimalShelter shelter = shelterManager.getShelters().remove(oldName);
        shelter.setShelterName(newName);
        shelterManager.getShelters().put(newName, shelter);
    }

    public void removeShelter(String shelterName) {
        shelterManager.removeShelter(shelterName);
    }
}
