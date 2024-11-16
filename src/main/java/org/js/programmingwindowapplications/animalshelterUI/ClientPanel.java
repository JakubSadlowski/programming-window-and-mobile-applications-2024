package org.js.programmingwindowapplications.animalshelterUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

public class ClientPanel extends AccountPanel {

    public void handleAdoptAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {

        } else {
            showAlert("Selection Error", "Please select an animal to adopt.");
        }
    }

    public void handleContactShelter() {
        showAlert("Contact", "You have contacted the shelter.");
    }

}
