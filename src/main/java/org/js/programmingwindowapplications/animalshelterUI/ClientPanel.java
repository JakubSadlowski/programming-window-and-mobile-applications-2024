package org.js.programmingwindowapplications.animalshelterUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
        if (selectedShelter != null) {
            String shelterName = selectedShelter.getShelterName();
            String phone = shelterFacade.getShelterPhoneNumber(shelterName);

            String contactDetails = String.format(
                    "Shelter: %s%nPhone: %s",
                    shelterName,
                    phone != null ? phone : "Not available"
            );

            showAlert("Contact Shelter", contactDetails);
        } else {
            showAlert("Contact Error", "Please select a shelter to contact.");
        }
    }

    @FXML
    private void handleRate() {
        if (selectedShelter == null) {
            showAlert("Error", "Please select a shelter to rate");
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Rate Shelter - " + selectedShelter.getShelterName());
        dialog.setHeaderText("Please rate your experience");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Rating value spinner
        Spinner<Integer> ratingSpinner = new Spinner<>(0, 5, 3);
        ratingSpinner.setEditable(true);

        // Comment area
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Enter your comment here...");
        commentArea.setPrefRowCount(3);

        grid.add(new Label("Rating (0-5):"), 0, 0);
        grid.add(ratingSpinner, 1, 0);
        grid.add(new Label("Comment:"), 0, 1);
        grid.add(commentArea, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButton) {
                shelterFacade.addRating(
                        selectedShelter.getShelterName(),
                        ratingSpinner.getValue(),
                        commentArea.getText().trim()
                );

                showAlert("Success", "Rating submitted successfully");
            }
            return null;
        });

        dialog.showAndWait();
    }
}
