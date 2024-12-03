package org.js.programmingwindowapplications.animalshelter;

import org.js.programmingwindowapplications.db.dao.AnimalDAO;
import org.js.programmingwindowapplications.db.dao.AnimalShelterDAO;
import org.js.programmingwindowapplications.db.entities.AnimalEntity;
import org.js.programmingwindowapplications.db.entities.AnimalShelterEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShelterManager {
    private final AnimalDAO animalDAO;
    private final AnimalShelterDAO shelterDAO;

    public ShelterManager(AnimalDAO animalDAO, AnimalShelterDAO shelterDAO) {
        this.animalDAO = animalDAO;
        this.shelterDAO = shelterDAO;
    }

    public AnimalShelter getShelter(String name) {
        return shelterDAO.findByName(name)
                .map(AnimalShelterEntity::toAnimalShelter)
                .orElse(null);
    }

    public Map<String, AnimalShelter> getShelters() {
        return shelterDAO.findAll().stream()
                .filter(shelterEntity -> shelterEntity.getShelterName() != null)
                .map(this::convertToDomain)
                .collect(Collectors.toMap(
                        AnimalShelter::getShelterName,
                        shelter -> shelter,
                        (existing, replacement) -> existing
                ));
    }

    private AnimalShelter convertToDomain(AnimalShelterEntity entity) {
        return new AnimalShelter(
                entity.getShelterName(),
                entity.getMaxCapacity(),
                entity.getPhoneNumber()
        );
    }

    public List<Animal> getAnimalsFromShelter(String shelterName) {
        return shelterDAO.findByName(shelterName)
                .map(shelter -> shelter.getAnimals().stream()
                        .map(AnimalEntity::toAnimal)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));
    }

    public boolean addShelter(String name, int capacity, String phoneNumber) {
        if (shelterDAO.findByName(name).isPresent()) {
            System.out.println("Shelter with name " + name + " already exists.");
            return false;
        }

        AnimalShelterEntity newShelter = new AnimalShelterEntity();
        newShelter.setShelterName(name);
        newShelter.setMaxCapacity(capacity);
        newShelter.setPhoneNumber(phoneNumber);

        shelterDAO.save(newShelter);
        return true;
    }

    public void addAnimal(String shelterName, String name, String species,
                          AnimalCondition condition, int age, double price) {
        AnimalShelterEntity shelter = shelterDAO.findByName(shelterName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));

        boolean animalExists = shelter.getAnimals().stream()
                .anyMatch(a -> a.getName().equals(name) &&
                        a.getSpecies().equals(species) &&
                        a.getAge() == age);

        if (animalExists) {
            return;
        }

        AnimalEntity newAnimal = new AnimalEntity();
        newAnimal.setName(name);
        newAnimal.setSpecies(species);
        newAnimal.setCondition(condition);
        newAnimal.setAge(age);
        newAnimal.setPrice(price);
        newAnimal.setShelter(shelter);

        animalDAO.save(newAnimal);
        shelter.addAnimal(newAnimal);
        shelterDAO.update(shelter);
    }

    public void modifyShelter(String oldName, String newName) {
        AnimalShelterEntity shelter = shelterDAO.findByName(oldName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + oldName));

        if (!oldName.equals(newName) && shelterDAO.findByName(newName).isPresent()) {
            throw new IllegalArgumentException("Shelter with name " + newName + " already exists");
        }

        shelter.setShelterName(newName);
        shelterDAO.update(shelter);
    }

    public void modifyAnimal(String shelterName, String oldName, String newName,
                             String newSpecies, AnimalCondition newCondition,
                             int newAge, double newPrice) {
        AnimalShelterEntity shelter = shelterDAO.findByName(shelterName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));

        AnimalEntity animal = shelter.getAnimals().stream()
                .filter(a -> a.getName().equals(oldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animal not found: " + oldName));

        animal.setName(newName);
        animal.setSpecies(newSpecies);
        animal.setCondition(newCondition);
        animal.setAge(newAge);
        animal.setPrice(newPrice);

        animalDAO.update(animal);
    }

    public void removeShelter(String name) {
        shelterDAO.findByName(name).ifPresent(shelterDAO::delete);
    }

    public void removeAnimal(String shelterName, Animal animal) {
        AnimalShelterEntity shelter = shelterDAO.findByName(shelterName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));

        AnimalEntity animalEntity = shelter.getAnimals().stream()
                .filter(a -> a.getName().equals(animal.getName()) &&
                        a.getSpecies().equals(animal.getSpecies()) &&
                        a.getAge() == animal.getAge())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animal not found in shelter: " + animal.getName()));

        shelter.getAnimals().remove(animalEntity);
        shelterDAO.update(shelter);
    }

    public Animal adoptAnimal(String shelterName, Animal animal) {
        AnimalShelterEntity shelter = shelterDAO.findByName(shelterName)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found: " + shelterName));

        AnimalEntity animalEntity = shelter.getAnimals().stream()
                .filter(a -> a.getName().equals(animal.getName()) &&
                        a.getSpecies().equals(animal.getSpecies()) &&
                        a.getAge() == animal.getAge())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animal not found in shelter: " + animal.getName()));

        animalEntity.setCondition(AnimalCondition.ADOPTED);
        animalDAO.update(animalEntity);
        shelter.getAnimals().remove(animalEntity);
        shelterDAO.update(shelter);
        animal.setCondition(AnimalCondition.ADOPTED);
        return animal;
    }

    public List<AnimalShelter> findEmpty() {
        return shelterDAO.findEmpty().stream()
                .map(AnimalShelterEntity::toAnimalShelter)
                .collect(Collectors.toList());
    }

    public void summary() {
        shelterDAO.findAll().forEach(shelter -> {
            double percentageFilled = ((double) shelter.getAnimals().size() / shelter.getMaxCapacity()) * 100.0;
            System.out.printf("\nShelter: %s, Percentage Filled: %.1f%%\n",
                    shelter.getShelterName(), percentageFilled);
            shelter.getAnimals().forEach(animal ->
                    System.out.printf("- %s (%s): %s, Age: %d, Price: %.2f\n",
                            animal.getName(), animal.getSpecies(),
                            animal.getCondition(), animal.getAge(), animal.getPrice())
            );
        });
    }
}