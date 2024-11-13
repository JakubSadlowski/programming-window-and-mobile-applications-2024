package org.js.programmingwindowapplications.animalshelterUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

public class ClientPanel extends Panel{

    private AnimalShelter selectedShelter;

    @FXML
    private ListView<Animal> animalList;

    // Set the selected shelter for the client controller
    public void setSelectedShelter(AnimalShelter shelter) {
        selectedShelter = shelter;
        loadAnimals();
    }

    // Load animals from the selected shelter
    public void loadAnimals() {
        animalList.getItems().clear();
        //animalList.getItems().addAll(selectedShelter.getAnimals());
    }

    // Handle the adoption request
    public void handleAdoptAnimal(ActionEvent actionEvent) {
        Animal selectedAnimal = animalList.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            requestAdoption(selectedAnimal);
        } else {
            showAlert("Selection Error", "Please select an animal to adopt.");
        }
    }

    // Handle contact with shelter
    public void handleContactShelter(ActionEvent actionEvent) {
        showAlert("Contact", "You have contacted the shelter.");
    }

    // Request adoption for the selected animal
    private void requestAdoption(Animal animal) {
        showAlert("Adoption Request", "You have requested to adopt: " + animal.getName());
    }

}
