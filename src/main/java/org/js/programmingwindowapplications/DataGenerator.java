package org.js.programmingwindowapplications;

import jakarta.persistence.EntityManager;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;
import org.js.programmingwindowapplications.animalshelterUI.AccountsManager;
import org.js.programmingwindowapplications.animalshelterUI.AdminAccount;
import org.js.programmingwindowapplications.animalshelterUI.ClientAccount;
import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.dao.RatingDAO;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.AnimalShelterDAOImpl;
import org.js.programmingwindowapplications.db.dao.implementation.RatingDAOImpl;
import org.js.programmingwindowapplications.db.HibernateUtil;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;
import org.js.programmingwindowapplications.io.ShelterIO;

public class DataGenerator {
    private static DataGenerator instance;
    private final AnimalShelterDAO shelterDAO = new AnimalShelterDAOImpl();
    private final AnimalDAO animalDAO = new AnimalDAOImpl();
    private final RatingDAO ratingDAO;
    private final ShelterManager manager;
    private final ShelterIO shelterIO = new ShelterIO();

    private DataGenerator() {
        this.ratingDAO = new RatingDAOImpl();
        this.manager = new ShelterManager(animalDAO, shelterDAO, ratingDAO, shelterIO);
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

        try {
            Thread.sleep(100); // Small delay to ensure previous transaction is complete
            addSampleRatings();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return manager;
    }

    public void addSampleRatings() {
        String[][] ratings = {
                {"Wild Paws", "5", "Fantastic care for animals, very clean facility!"},
                {"Wild Paws", "4", "Professional staff, but waiting times can be long"},
                {"Wild Paws", "5", "Helped us find our perfect companion"},
                {"Green Meadows", "3", "Decent facility but could use more staff"},
                {"Green Meadows", "4", "Good selection of animals, caring environment"},
                {"Green Meadows", "5", "Very knowledgeable about animal care"},
                {"Happy Tails Rescue", "5", "Best shelter in the area!"},
                {"Happy Tails Rescue", "5", "Amazing dedication to animal welfare"},
                {"Happy Tails Rescue", "4", "Great adoption process"},
                {"Forest Friends", "4", "Nice facility, friendly staff"},
                {"Forest Friends", "3", "Good but could improve cleanliness"},
                {"Forest Friends", "5", "They really care about the animals"},
                {"Sunset Haven", "4", "Wonderful experience adopting our cat"},
                {"Sunset Haven", "3", "Limited space but good care"},
                {"Sunset Haven", "4", "Helpful staff, good follow-up"},
                {"Ocean Breeze Shelter", "5", "Modern facilities, excellent care"},
                {"Ocean Breeze Shelter", "4", "Great variety of animals"},
                {"Ocean Breeze Shelter", "5", "Outstanding adoption support"}
        };

        for (String[] rating : ratings) {
            try {
                if (!ratingExists(rating[0], rating[2])) {
                    manager.addRating(
                            rating[0],
                            Integer.parseInt(rating[1]),
                            rating[2]
                    );
                    Thread.sleep(50); // Small delay between ratings to avoid transaction conflicts
                }
            } catch (Exception e) {
                System.err.println("Error adding rating for " + rating[0] + ": " + e.getMessage());
            }
        }
    }

    private boolean ratingExists(String shelterName, String comment) {
        AnimalShelterEntity shelter = shelterDAO.findByName(shelterName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));

        return ratingDAO.findByShelter(shelter.getId()).stream()
                .anyMatch(r -> r.getComment().equals(comment));
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