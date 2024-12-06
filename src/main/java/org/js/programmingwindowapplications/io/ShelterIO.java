package org.js.programmingwindowapplications.io;

import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShelterIO {
    public void exportToCSV(List<Animal> animals, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Use reflection to get field names
            Field[] fields = Animal.class.getDeclaredFields();
            String header = Arrays.stream(fields)
                    .map(Field::getName)
                    .collect(Collectors.joining(","));
            writer.println(header);

            for (Animal animal : animals) {
                String line = Arrays.stream(fields)
                        .map(field -> {
                            try {
                                field.setAccessible(true);
                                return String.valueOf(field.get(animal));
                            } catch (IllegalAccessException e) {
                                return "";
                            }
                        })
                        .collect(Collectors.joining(","));
                writer.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error exporting to CSV", e);
        }
    }

    public List<Animal> importFromCSV(String filename) {
        List<Animal> animals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] headers = reader.readLine().split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Animal animal = new Animal();

                for (int i = 0; i < headers.length; i++) {
                    Field field = Animal.class.getDeclaredField(headers[i]);
                    field.setAccessible(true);
                    setFieldValue(field, animal, values[i]);
                }

                animals.add(animal);
            }
            return animals;
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error importing from CSV", e);
        }
    }

    private void setFieldValue(Field field, Animal animal, String value)
            throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type == String.class) {
            field.set(animal, value);
        } else if (type == int.class) {
            field.set(animal, Integer.parseInt(value));
        } else if (type == double.class) {
            field.set(animal, Double.parseDouble(value));
        } else if (type == AnimalCondition.class) {
            field.set(animal, AnimalCondition.valueOf(value));
        }
    }
}
