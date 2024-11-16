package org.js.programmingwindowapplications.animalshelter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShelterManager {
    private final Map<String, AnimalShelter> shelters;

    public ShelterManager() {
        shelters = new HashMap<>();
    }

    public AnimalShelter getShelter(String name) {
        return shelters.get(name);
    }

    public Map<String, AnimalShelter> getShelters() {
        return shelters;
    }

    public List<Animal> getAnimalsFromShelter(String shelterName) {
        return shelters.get(shelterName).getAnimalList();
    }

    public boolean addShelter(String name, int capacity) {
        if (shelters.containsKey(name)) {
            System.out.println("Shelter with name " + name + " already exists.");
            return false;
        }
        shelters.put(name, new AnimalShelter(name, capacity));
        return true;
    }

    public void addAnimal(String shelterName, String name, String species, AnimalCondition condition, int age, double price) {
        AnimalShelter shelter = shelters.get(shelterName);
        if (shelter != null) {
            Animal newAnimal = new Animal(name, species, condition, age, price);
            shelter.addAnimal(newAnimal);
        } else {
            throw new IllegalArgumentException("Shelter not found: " + shelterName);
        }
    }

    public void modifyShelter(String oldName, String newName) {
        if (!shelters.containsKey(oldName)) {
            throw new IllegalArgumentException("Shelter with the name " + oldName + " does not exist.");
        }
        if (shelters.containsKey(newName)) {
            throw new IllegalArgumentException("A shelter with the name " + newName + " already exists.");
        }

        AnimalShelter shelter = shelters.remove(oldName);
        shelter.setShelterName(newName);
        shelters.put(newName, shelter);
    }

    public void modifyAnimal(String shelterName, String oldName, String newName, String newSpecies, AnimalCondition newCondition, int newAge, double newPrice) {
        AnimalShelter shelter = getShelter(shelterName);
        if (shelter == null) {
            throw new IllegalArgumentException("Shelter " + shelterName + " does not exist.");
        }

        Animal animal = shelter.getAnimalList().stream()
                .filter(a -> a.getName().equals(oldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animal with the name " + oldName + " not found in the shelter."));

        animal.setName(newName);
        animal.setSpecies(newSpecies);
        animal.setCondition(newCondition);
        animal.setAge(newAge);
        animal.setPrice(newPrice);
    }

    public AnimalShelter removeShelter(String name) {
        if (!shelters.containsKey(name)) {
            System.out.println("Shelter with name " + name + " does not exist.");
        }
        return shelters.remove(name);
    }

    public List<AnimalShelter> findEmpty() {
        return shelters.values().stream().filter(AnimalShelter::isEmpty).collect(Collectors.toList());
    }

    public void summary() {
        for (Map.Entry<String, AnimalShelter> entry : shelters.entrySet()) {
            String shelterName = entry.getKey();
            AnimalShelter shelter = entry.getValue();
            double percentageFilled = ((double) shelter.getCapacity() / shelter.getMaxCapacity()) * 100.0;
            System.out.print("\nShelter: " + shelterName + ", Percentage Filled: " + percentageFilled + "%\n");
            shelter.summary();
        }
    }
}
