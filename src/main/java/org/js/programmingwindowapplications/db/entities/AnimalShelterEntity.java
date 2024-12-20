package org.js.programmingwindowapplications.db.entities;

import jakarta.persistence.*;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelters")
public class AnimalShelterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shelterName;
    private int maxCapacity;
    private String phoneNumber;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnimalEntity> animals = new ArrayList<>();

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RatingEntity> ratings = new ArrayList<>();

    public AnimalShelterEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<AnimalEntity> getAnimals() { return animals; }
    public void setAnimals(List<AnimalEntity> animals) { this.animals = animals; }

    public List<RatingEntity> getRatings() { return ratings; }
    public void setRatings(List<RatingEntity> ratings) { this.ratings = ratings; }

    public void addAnimal(AnimalEntity animal) {
        if (animal != null && !animals.contains(animal)) {
            animals.add(animal);
            animal.setShelter(this);  
        }
    }

    public AnimalShelter toAnimalShelter() {
        AnimalShelter shelter =
                new AnimalShelter(
                        shelterName, maxCapacity, phoneNumber
                );

        for (AnimalEntity animalEntity : animals) {
            shelter.addAnimal(animalEntity.toAnimal());
        }

        return shelter;
    }
}
