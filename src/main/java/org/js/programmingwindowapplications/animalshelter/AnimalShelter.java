package org.js.programmingwindowapplications.animalshelter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalShelter {
    private final String shelterName;
    private final List<Animal> animalList;
    private final int maxCapacity;

    public AnimalShelter(String shelterName, int maxCapacity) {
        this.shelterName = shelterName;
        this.maxCapacity = maxCapacity;
        this.animalList = new ArrayList<>();
    }

    public int getCapacity() {
        return animalList.size();
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getShelterName() {
        return shelterName;
    }

    public Animal getAnimalFromShelter(Animal animal) {
        if (!animalList.isEmpty()) {
            return animalList.get(animalList.indexOf(animal));
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return animalList.isEmpty();
    }

    public boolean addAnimal(Animal animal) {
        if (animalList.size() >= maxCapacity) {
            System.err.println("Shelter is at full capacity! Cannot add more animals.");
            return false;
        }

        for (Animal a : animalList) {
            if (a.getName().equals(animal.getName()) &&
                    a.getSpecies().equals(animal.getSpecies()) &&
                    a.getAge() == animal.getAge()) {
                System.out.println("This animal already exists in the shelter: "
                        + animal.getName() + ", " + animal.getSpecies() + ", age " + animal.getAge());
                return false;
            }
        }
        animalList.add(animal);
        return true;
    }

    public Animal adoptAnimal(Animal animal) {
        if (animalList.contains(animal)) {
            changeCondition(animal, AnimalCondition.ADOPTED);
            removeAnimal(animal);
            return animal;
        } else {
            System.err.println("Animal " + animal.getName() + " does not exist in the shelter.");
            return null;
        }
    }

    public void changeCondition(Animal animal, AnimalCondition condition) {
        animal.setCondition(condition);
    }

    public void removeAnimal(Animal animal) {
        animalList.removeIf(a -> a.getName().equals(animal.getName()) &&
                a.getSpecies().equals(animal.getSpecies()) &&
                a.getAge() == animal.getAge());
    }

    public void changeAge(Animal animal, int newAge) {
        if (animalList.contains(animal)) {
            if (newAge > 0) {
                animal.setAge(newAge);
            } else {
                animal.setAge(animal.getAge() + 1);
            }
        }
    }

    public int countByCondition(AnimalCondition condition) {
        int conditionCount = 0;
        for (Animal a : animalList) {
            if (a.getCondition().equals(condition)) {
                conditionCount++;
            }
        }
        return conditionCount;
    }

    public List<Animal> sortByName() {
        List<Animal> sortedAnimals = new ArrayList<>(animalList);
        sortedAnimals.sort(Animal::compareTo);
        return sortedAnimals;
    }

    public List<Animal> sortByPrice() {
        List<Animal> sortedAnimals = new ArrayList<>(animalList);
        sortedAnimals.sort(Comparator.comparingDouble(Animal::getPrice));
        return sortedAnimals;
    }

    public String search(String name) {
        return animalList.stream()
                .filter(a -> a.getName().equals(name))
                .map(a -> "Name: " + a.getName() + ", Species: " + a.getSpecies() + ", Age: " + a.getAge() + ", Price: " + a.getPrice())
                .findFirst()
                .orElse("Animal with name " + name + " not found.");
    }

    public List<Animal> searchPartial(String fragment){
        return animalList.stream()
                .filter(a -> a.getName().toLowerCase().contains(fragment.toLowerCase()) ||
                        a.getSpecies().toLowerCase().contains(fragment.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void summary() {
        System.out.println("Shelter: " + shelterName);
        for (Animal a : animalList) {
            a.print();
        }
    }

    public Animal max() {
        if (animalList.isEmpty()) {
            return null;
        }
        return Collections.max(animalList, Comparator.comparingDouble(Animal::getPrice));
    }

    @Override
    public String toString() {
        return shelterName;
    }
}
