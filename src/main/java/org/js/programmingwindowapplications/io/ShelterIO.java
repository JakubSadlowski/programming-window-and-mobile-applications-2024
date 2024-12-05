package org.js.programmingwindowapplications.io;

import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShelterIO {
    public void serializeShelters(List<AnimalShelter> shelters, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            out.writeObject(shelters);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing shelters", e);
        }
    }

    public List<AnimalShelter> deserializeShelters(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (List<AnimalShelter>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error deserializing shelters", e);
        }
    }

    public void exportToCSV(List<Animal> animals, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("name,species,condition,age,price");
            for (Animal animal : animals) {
                writer.printf("%s,%s,%s,%d,%.2f%n",
                        animal.getName(),
                        animal.getSpecies(),
                        animal.getCondition(),
                        animal.getAge(),
                        animal.getPrice()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Error exporting to CSV: " + e.getMessage(), e);
        }
    }

    public List<Animal> importFromCSV(String filename) {
        List<Animal> animals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                animals.add(new Animal(
                        parts[0],
                        parts[1],
                        AnimalCondition.valueOf(parts[2]),
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])
                ));
            }
            return animals;
        } catch (IOException e) {
            throw new RuntimeException("Error importing from CSV: " + e.getMessage(), e);
        }
    }
}
