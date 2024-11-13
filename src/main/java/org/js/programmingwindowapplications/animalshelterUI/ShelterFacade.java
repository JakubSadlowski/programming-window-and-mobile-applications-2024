package org.js.programmingwindowapplications.animalshelterUI;

import org.js.programmingwindowapplications.animalshelter.Animal;
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
}
