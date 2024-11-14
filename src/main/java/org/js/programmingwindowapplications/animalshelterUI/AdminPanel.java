package org.js.programmingwindowapplications.animalshelterUI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.js.programmingwindowapplications.animalshelter.Animal;

import java.util.Optional;

public class AdminPanel extends AccountPanel {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;

    @FXML
    private void handleAddAnimal() {
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

    @FXML
    private void handleEditAnimal() {
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
