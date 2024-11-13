package org.js.programmingwindowapplications.animalshelterUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.js.programmingwindowapplications.animalshelter.Animal;

public class AdminPanel extends Panel {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;


    public void handleAddAnimal() {
        /*String name = nameField.getText();
        String type = typeField.getText();
        if (name.isEmpty() || type.isEmpty()) {
            showAlert("Input Error", "Please provide both name and type.");
        } else {
            Animal newAnimal = new Animal(name, type); // Assuming Animal has a constructor like this
            selectedShelter.addAnimal(newAnimal); // Add the animal to the shelter
            loadAnimals(selectedShelter); // Refresh the list
        }*/
    }


    public void handleEditAnimal() {
        /*Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            String newName = nameField.getText();
            String newType = typeField.getText();
            if (!newName.isEmpty() && !newType.isEmpty()) {
                selectedAnimal.setName(newName);
                selectedAnimal.setType(newType);
                loadAnimals(selectedShelter); // Refresh the list
            } else {
                showAlert("Input Error", "Please provide both new name and type.");
            }
        } else {
            showAlert("Selection Error", "Please select an animal to edit.");
        }*/
    }


    public void handleDeleteAnimal() {
        /*Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            selectedShelter.removeAnimal(selectedAnimal);
            loadAnimals(selectedShelter);
        } else {
            showAlert("Selection Error", "Please select an animal to delete.");
        }*/
    }


    public void handleAdoptAnimal() {

    }


    public void handleContactShelter() {

    }
}
