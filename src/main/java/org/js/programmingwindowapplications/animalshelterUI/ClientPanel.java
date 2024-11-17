package org.js.programmingwindowapplications.animalshelterUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;

import java.util.Optional;

public class ClientPanel extends AccountPanel {

    public void handleAdoptAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

        if (selectedAnimal == null) {
            showAlert("Selection Error", "Please select an animal to adopt.");
            return;
        }

        if (selectedAnimal.getCondition() == AnimalCondition.ADOPTED) {
            showAlert("Adoption Error", "This animal has already been adopted.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Adoption Confirmation");
        confirmationAlert.setHeaderText("Adopt Animal");
        confirmationAlert.setContentText("Are you sure you want to adopt: " + selectedAnimal.getName() + "?");

        ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            try {
                Animal adoptedAnimal = shelterFacade.adoptAnimal(selectedShelter.getShelterName(), selectedAnimal);

                if (adoptedAnimal != null) {
                    showAlert("Adoption Success", "You have successfully adopted: " + adoptedAnimal.getName());
                    loadAnimals(selectedShelter);
                } else {
                    showAlert("Adoption Error", "The adoption process failed. Please try again.");
                }
            } catch (Exception e) {
                showAlert("Unexpected Error", "An error occurred while trying to adopt the animal.");
                e.printStackTrace();
            }
        }
    }

    public void handleContactShelter() {
        /*if (selectedShelter != null) {
            String shelterName = selectedShelter.getShelterName();
            String phone = selectedShelter.getPhoneNumber(); // Przykład pola w klasie Shelter
            String email = selectedShelter.getEmail();       // Przykład pola w klasie Shelter
            String address = selectedShelter.getAddress();   // Przykład pola w klasie Shelter

            String contactDetails = String.format(
                    "Shelter: %s%nPhone: %s%nEmail: %s%nAddress: %s",
                    shelterName,
                    phone != null ? phone : "Not available",
                    email != null ? email : "Not available",
                    address != null ? address : "Not available"
            );

            showAlert("Contact Shelter", contactDetails);
        } else {
            showAlert("Contact Error", "Please select a shelter to contact.");
        }*/
    }
}
