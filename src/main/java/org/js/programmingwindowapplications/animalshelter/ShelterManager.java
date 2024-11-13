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

    public boolean addShelter(String name, int capacity) {
        if (shelters.containsKey(name)) {
            System.out.println("Shelter with name " + name + " already exists.");
            return false;
        }
        shelters.put(name, new AnimalShelter(name, capacity));
        return true;
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
