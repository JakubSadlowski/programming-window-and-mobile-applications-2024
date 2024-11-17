package org.js.programmingwindowapplications.animalshelter;

import java.util.List;

public class Lab2 {
    public static void execute(){
        try {
            // Create ShelterManager
            ShelterManager manager = new ShelterManager();
            manager.addShelter("Dog House", 5, "555-221-332");
            manager.addShelter("Cat House", 3, "541-621-347");

            // Create animals
            Animal animal1 = new Animal("Buddy", "Dog", AnimalCondition.HEALTHY, 2, 150.0);
            Animal animal2 = new Animal("Mittens", "Cat", AnimalCondition.UNHEALTHY, 4, 75.0);
            Animal animal3 = new Animal("Whiskers", "Cat", AnimalCondition.HEALTHY, 3, 50.0);
            Animal animal4 = new Animal("Rex", "Dog", AnimalCondition.HEALTHY, 5, 120.0);

            // Add dogs to Dog House
            AnimalShelter dogHouse = manager.getShelter("Dog House");
            dogHouse.addAnimal(animal1);
            dogHouse.addAnimal(animal4);

            // Add cats to Cat House
            AnimalShelter catHouse = manager.getShelter("Cat House");
            catHouse.addAnimal(animal2);
            catHouse.addAnimal(animal3);

            // Summary of all shelters in manager
            manager.summary();

            // Adopt dog from Dog House
            Animal adoptedAnimal = dogHouse.adoptAnimal(animal1);
            System.out.println("\nAdopted dog from Dog House shelter: ");
            adoptedAnimal.print();
            manager.summary();

            // Quarantine Whiskers from Cat House
            catHouse.changeCondition(animal3, AnimalCondition.QUARANTINE);
            Animal quarantinedCat = catHouse.getAnimalFromShelter(animal3);
            System.out.println("\nQuarantined cat from Dog House shelter: ");
            quarantinedCat.print();

            // Change age of Mittens to 5 years old
            System.out.println("\nChange age of Mittens to 5 years old:");
            catHouse.changeAge(animal2, 5);
            catHouse.summary();

            // Check how many healthy dogs are there in Dog House
            int healthyDogsCounter = dogHouse.countByCondition(AnimalCondition.HEALTHY);
            System.out.println("\nAmount of healthy dogs in Dog House: " + healthyDogsCounter);

            // Sort cats by name
            System.out.println("\nAnimals sorted by name:");
            List<Animal> sortedByName = catHouse.sortByName();
            for (Animal a : sortedByName) {
                a.print();
            }

            // Sort cats by price
            System.out.println("\nAnimals sorted by price:");
            List<Animal> sortedByPrice = catHouse.sortByPrice();
            for (Animal a : sortedByPrice) {
                a.print();
            }

            // Search for Buddy in Dog House
            System.out.println("\nSearch result for 'Buddy': " + dogHouse.search("Buddy"));

            // List animals by partial name
            System.out.println("\nCats with 'itt' in name or species:");
            List<Animal> foundAnimals = catHouse.searchPartial("itt");
            for (Animal a : foundAnimals) {
                a.print();
            }

            // Show most expensive cat
            Animal maxPricedAnimal = catHouse.max();
            if (maxPricedAnimal != null) {
                System.out.println("\nMost expensive animal:");
                maxPricedAnimal.print();
            }

            // Find empty shelters
            System.out.println("\nEmpty shelters:");
            List<AnimalShelter> emptyShelters = manager.findEmpty();
            for (AnimalShelter s : emptyShelters) {
                System.out.println(s.getShelterName());
            }

            // Remove a shelter and display remaining shelters
            manager.removeShelter("Dog House");
            System.out.println("\nShelters after removing 'Dog House':");
            manager.summary();
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
