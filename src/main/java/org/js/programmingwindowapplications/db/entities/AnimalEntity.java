package org.js.programmingwindowapplications.db.entities;

import jakarta.persistence.*;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;

@Entity
@Table(name = "animals")
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private double price;
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_condition")
    private AnimalCondition condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id")
    private AnimalShelterEntity shelter;

    public AnimalEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public AnimalCondition getCondition() { return condition; }
    public void setCondition(AnimalCondition condition) { this.condition = condition; }

    public AnimalShelterEntity getShelter() { return shelter; }
    public void setShelter(AnimalShelterEntity shelter) { this.shelter = shelter; }

    public Animal toAnimal() {
        return new Animal(
                name, species, condition, age, price
        );
    }
}
