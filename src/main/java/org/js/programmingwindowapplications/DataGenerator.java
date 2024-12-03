package org.js.programmingwindowapplications;

import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;
import org.js.programmingwindowapplications.animalshelterUI.AccountsManager;
import org.js.programmingwindowapplications.animalshelterUI.AdminAccount;
import org.js.programmingwindowapplications.animalshelterUI.ClientAccount;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalShelterDAOImpl;

public class DataGenerator {
    private static DataGenerator instance;
    private final AnimalShelterDAO shelterDAO = new AnimalShelterDAOImpl();
    private final AnimalDAO animalDAO = new AnimalDAOImpl();
    private final ShelterManager manager;

    private DataGenerator() {
        this.manager = new ShelterManager(animalDAO, shelterDAO);
    }

    public static synchronized DataGenerator getInstance() {
        if (instance == null) {
            instance = new DataGenerator();
        }
        return instance;
    }

    public AccountsManager addAccounts() {
        AccountsManager accountsManager = new AccountsManager();
        accountsManager.addAccount(new AdminAccount());
        accountsManager.addAccount(new ClientAccount("Jakub", "Sadlowski", "sado", "sado"));
        return accountsManager;
    }

    public ShelterManager addShelters() {
        manager.addShelter("Wild Paws", 6, "555-123-456");
        manager.addShelter("Green Meadows", 4, "555-234-567");
        manager.addShelter("Happy Tails Rescue", 5, "555-345-678");
        manager.addShelter("Forest Friends", 7, "555-456-789");
        manager.addShelter("Sunset Haven", 3, "555-567-890");
        manager.addShelter("Ocean Breeze Shelter", 8, "555-678-901");

        addAnimalsToShelter("Wild Paws", new Animal[]{
                new Animal("Rocky", "Dog", AnimalCondition.HEALTHY, 3, 150.0),
                new Animal("Luna", "Cat", AnimalCondition.UNHEALTHY, 2, 80.0),
                new Animal("Charlie", "Rabbit", AnimalCondition.QUARANTINE, 1, 60.0)
        });

        addAnimalsToShelter("Green Meadows", new Animal[]{
                new Animal("Bella", "Dog", AnimalCondition.HEALTHY, 4, 120.0),
                new Animal("Max", "Cat", AnimalCondition.QUARANTINE, 2, 70.0),
                new Animal("Milo", "Rabbit", AnimalCondition.UNHEALTHY, 3, 50.0)
        });

        addAnimalsToShelter("Happy Tails Rescue", new Animal[]{
                new Animal("Zoe", "Dog", AnimalCondition.HEALTHY, 5, 180.0),
                new Animal("Oliver", "Cat", AnimalCondition.HEALTHY, 3, 100.0),
                new Animal("Ruby", "Rabbit", AnimalCondition.QUARANTINE, 2, 65.0)
        });

        addAnimalsToShelter("Forest Friends", new Animal[]{
                new Animal("Socks", "Dog", AnimalCondition.UNHEALTHY, 6, 110.0),
                new Animal("Misty", "Cat", AnimalCondition.HEALTHY, 1, 85.0),
                new Animal("Buddy", "Rabbit", AnimalCondition.HEALTHY, 4, 55.0)
        });

        addAnimalsToShelter("Sunset Haven", new Animal[]{
                new Animal("Shadow", "Dog", AnimalCondition.UNHEALTHY, 3, 95.0),
                new Animal("Toby", "Cat", AnimalCondition.HEALTHY, 2, 75.0),
                new Animal("Oscar", "Rabbit", AnimalCondition.QUARANTINE, 2, 50.0)
        });

        addAnimalsToShelter("Ocean Breeze Shelter", new Animal[]{
                new Animal("Lily", "Dog", AnimalCondition.HEALTHY, 4, 140.0),
                new Animal("Daisy", "Cat", AnimalCondition.UNHEALTHY, 3, 60.0),
                new Animal("Leo", "Rabbit", AnimalCondition.QUARANTINE, 1, 45.0),
                new Animal("Coco", "Dog", AnimalCondition.HEALTHY, 4, 130.0),
                new Animal("Chloe", "Cat", AnimalCondition.QUARANTINE, 5, 90.0)
        });

        return manager;
    }

    private void addAnimalsToShelter(String shelterName, Animal[] animals) {
        for (Animal animal : animals) {
            manager.addAnimal(
                    shelterName,
                    animal.getName(),
                    animal.getSpecies(),
                    animal.getCondition(),
                    animal.getAge(),
                    animal.getPrice()
            );
        }
    }
}