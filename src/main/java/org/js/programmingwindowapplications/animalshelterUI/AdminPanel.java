package org.js.programmingwindowapplications.animalshelterUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;

import java.util.Optional;

public class AdminPanel extends AccountPanel {

    @FXML
    private TextField nameField;
    @FXML
    private TextField speciesField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField priceField;
    private GridPane grid;

    @FXML
    private void handleAddAnimal() {
        showAddAnimalDialog();
    }

    private void showAddAnimalDialog() {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal");
        dialog.setHeaderText("Add a new animal");

        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        nameField = new TextField();
        speciesField = new TextField();
        ageField = new TextField();
        priceField = new TextField();
        ComboBox<AnimalCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(AnimalCondition.values());

        createAnimalGrid(conditionComboBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = nameField.getText();
                    String species = speciesField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    AnimalCondition condition = conditionComboBox.getValue();

                    if (name.isEmpty() || species.isEmpty() || condition == null) {
                        showAlert("Input Error", "Please fill out all fields.");
                        return null;
                    }
                    return new Animal(name, species, condition, age, price);
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for age and price.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newAnimal -> {
            if (selectedShelter != null) {
                selectedShelter.addAnimal(newAnimal);
                loadAnimals(selectedShelter);
            } else {
                showAlert("Error", "Please select a shelter first.");
            }
        });
    }

    @FXML
    private void handleEditAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

        if (selectedAnimal != null) {
            showEditAnimalDialog(selectedAnimal);
        } else {
            showAlert("Selection Error", "Please select an animal to edit.");
        }
    }

    private void showEditAnimalDialog(Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Edit Animal");
        dialog.setHeaderText("Edit details for: " + animal.getName());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        nameField = new TextField(animal.getName());
        speciesField = new TextField(animal.getSpecies());
        ageField = new TextField(String.valueOf(animal.getAge()));
        priceField = new TextField(String.valueOf(animal.getPrice()));
        ComboBox<AnimalCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(AnimalCondition.values());
        conditionComboBox.setValue(animal.getCondition());

        createAnimalGrid(conditionComboBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    animal.setName(nameField.getText());
                    animal.setSpecies(speciesField.getText());
                    animal.setAge(Integer.parseInt(ageField.getText()));
                    animal.setPrice(Double.parseDouble(priceField.getText()));
                    animal.setCondition(conditionComboBox.getValue());
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for age and price.");
                    return null;
                }
                return animal;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedAnimal -> loadAnimals(selectedShelter));
    }

    private void createAnimalGrid(ComboBox<AnimalCondition> conditionComboBox) {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Species:"), 0, 1);
        grid.add(speciesField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(ageField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);
        grid.add(new Label("Condition:"), 0, 4);
        grid.add(conditionComboBox, 1, 4);
    }

    @FXML
    private void handleDeleteAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

        if (selectedAnimal != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Are you sure you want to delete this animal?");
            confirmationAlert.setContentText("Animal: " + selectedAnimal.getName());

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                selectedShelter.removeAnimal(selectedAnimal);
                loadAnimals(selectedShelter);
            }
        } else {
            showAlert("Selection Error", "Please select an animal to delete.");
        }
    }
}
