package org.js.programmingwindowapplications.animalshelter;

import java.io.Serial;
import java.io.Serializable;

public class Animal implements Comparable<Animal>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String species;
    private double price;
    private AnimalCondition condition;
    private int age;

    public Animal(String name, String species, AnimalCondition condition, int age, double price) {
        this.name = name;
        this.species = species;
        this.condition = condition;
        this.age = age;
        this.price = price;
    }

    public Animal() {

    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public AnimalCondition getCondition() {
        return condition;
    }

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Animal o) {
        int nameComparison = this.name.compareTo(o.name);

        if (nameComparison != 0) {
            return nameComparison;
        }

        int speciesComparison = this.species.compareTo(o.species);

        if (speciesComparison != 0) {
            return speciesComparison;
        }

        return Integer.compare(this.age, o.age);
    }

    public void print() {
        System.out.print("Name: " + name + ", Species: " + species + ", Condition: " + condition + ", Age: " + age + ", Price: " + price + "\n");
    }
}
