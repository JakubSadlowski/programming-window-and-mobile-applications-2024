package org.js.programmingwindowapplications.animalshelterUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.db.entities.RatingEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class AdminPanel extends AccountPanel {

    @FXML
    private TextField animalNameField;
    @FXML
    private TextField animalSpeciesField;
    @FXML
    private TextField animalAgeField;
    @FXML
    private TextField animalPriceField;
    @FXML
    private TextField shelterNameField;
    @FXML
    private TextField shelterMaxCapacityField;
    @FXML
    private TextField shelterPhoneNumberField;
    private GridPane grid;

    @FXML
    private void handleAdd() {
        Alert choiceDialog = new Alert(Alert.AlertType.CONFIRMATION);
        choiceDialog.setTitle("Add Option");
        choiceDialog.setHeaderText("Choose an option");
        choiceDialog.setContentText("Would you like to add an animal or a shelter?");

        ButtonType addAnimalButton = new ButtonType("Add Animal");
        ButtonType addShelterButton = new ButtonType("Add Shelter");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        choiceDialog.getButtonTypes().setAll(addAnimalButton, addShelterButton, cancelButton);

        choiceDialog.showAndWait().ifPresent(choice -> {
            if (choice == addAnimalButton) {
                showAddAnimalDialog();
            } else if (choice == addShelterButton) {
                showAddShelterDialog();
            }
        });
    }

    private void showAddAnimalDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Animal");
        dialog.setHeaderText("Add a new animal");

        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        animalNameField = new TextField();
        animalSpeciesField = new TextField();
        animalAgeField = new TextField();
        animalPriceField = new TextField();
        ComboBox<AnimalCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(
                Arrays.stream(AnimalCondition.values())
                        .filter(condition -> condition != AnimalCondition.ADOPTED)
                        .toList()
        );

        createAnimalGrid(conditionComboBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = animalNameField.getText();
                    String species = animalSpeciesField.getText();
                    int age = Integer.parseInt(animalAgeField.getText());
                    double price = Double.parseDouble(animalPriceField.getText());
                    AnimalCondition condition = conditionComboBox.getValue();

                    if (name.isEmpty() || species.isEmpty() || condition == null) {
                        showAlert("Input Error", "Please fill out all fields.");
                        return null;
                    }

                    if (selectedShelter != null) {
                        shelterFacade.addAnimal(
                                selectedShelter.getShelterName(),
                                name,
                                species,
                                condition,
                                age,
                                price
                        );
                        loadAnimals(selectedShelter);
                    } else {
                        showAlert("Error", "Please select a shelter first.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for age and price.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAddShelterDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Shelter");
        dialog.setHeaderText("Add a new shelter");

        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        shelterNameField = new TextField();
        shelterMaxCapacityField = new TextField();
        shelterPhoneNumberField = new TextField();

        createShelterGrid();

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = shelterNameField.getText();
                    int maxCapacity = Integer.parseInt(shelterMaxCapacityField.getText());
                    String phoneNumber = shelterPhoneNumberField.getText();

                    if (!phoneNumber.matches("\\d{3}-\\d{3}-\\d{3}")) {
                        showAlert("Input Error", "Phone number must be in the format ddd-ddd-ddd.");
                        return null;
                    }

                    if (name.isEmpty()) {
                        showAlert("Input Error", "Shelter name cannot be empty.");
                        return null;
                    }
                    if (maxCapacity <= 0) {
                        showAlert("Input Error", "Max capacity must be a positive number.");
                        return null;
                    }

                    shelterFacade.addShelter(name, maxCapacity, phoneNumber);
                    loadShelters();
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter a valid number for max capacity.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void createShelterGrid() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Shelter Name:"), 0, 0);
        grid.add(shelterNameField, 1, 0);
        grid.add(new Label("Max Capacity:"), 0, 1);
        grid.add(shelterMaxCapacityField, 1, 1);
        grid.add(new Label("Phone Number:"), 0, 2);
        grid.add(shelterPhoneNumberField, 1, 2);
    }


    @FXML
    private void handleEdit() {
        selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        Alert choiceDialog = new Alert(Alert.AlertType.CONFIRMATION);
        choiceDialog.setTitle("Edit Option");
        choiceDialog.setHeaderText("Choose an option");
        choiceDialog.setContentText("Would you like to edit an animal or a shelter?");

        ButtonType editAnimalButton = new ButtonType("Edit Animal");
        ButtonType editShelterButton = new ButtonType("Edit Shelter");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        choiceDialog.getButtonTypes().setAll(editAnimalButton, editShelterButton, cancelButton);

        choiceDialog.showAndWait().ifPresent(choice -> {
            if (choice == editAnimalButton) {
                showEditAnimalDialog(selectedAnimal);
            } else if (choice == editShelterButton) {
                showEditShelterDialog();
            }
        });
    }

    private void showEditAnimalDialog(Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Edit Animal");
        dialog.setHeaderText("Edit details for: " + animal.getName());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        animalNameField = new TextField(animal.getName());
        animalSpeciesField = new TextField(animal.getSpecies());
        animalAgeField = new TextField(String.valueOf(animal.getAge()));
        animalPriceField = new TextField(String.valueOf(animal.getPrice()));
        ComboBox<AnimalCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(AnimalCondition.values());
        conditionComboBox.setValue(animal.getCondition());

        createAnimalGrid(conditionComboBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String newName = animalNameField.getText();
                    String newSpecies = animalSpeciesField.getText();
                    int newAge = Integer.parseInt(animalAgeField.getText());
                    double newPrice = Double.parseDouble(animalPriceField.getText());
                    AnimalCondition newCondition = conditionComboBox.getValue();

                    if (newName.isEmpty() || newSpecies.isEmpty()) {
                        showAlert("Input Error", "Name and species cannot be empty.");
                        return null;
                    }

                    if (newCondition == AnimalCondition.ADOPTED) {
                        shelterFacade.removeAnimal(selectedShelter.getShelterName(), animal);
                        loadAnimals(selectedShelter);
                        showAlert("Animal Removed", "The animal has been removed as it is now marked as ADOPTED.");
                        return null;
                    }

                    shelterFacade.modifyAnimal(
                            selectedShelter.getShelterName(),
                            animal.getName(),
                            newName,
                            newSpecies,
                            newCondition,
                            newAge,
                            newPrice
                    );

                    return animal;
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for age and price.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedAnimal -> loadAnimals(selectedShelter));
    }

    private void showEditShelterDialog() {
        if (selectedShelter != null) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Edit Shelter");
            dialog.setHeaderText("Edit details for: " + selectedShelter.getShelterName());

            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            shelterNameField = new TextField(selectedShelter.getShelterName());
            shelterMaxCapacityField = new TextField(String.valueOf(selectedShelter.getMaxCapacity()));
            shelterPhoneNumberField = new TextField(selectedShelter.getPhoneNumber());

            createShelterGrid();

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        String newName = shelterNameField.getText().trim();
                        int newCapacity = Integer.parseInt(shelterMaxCapacityField.getText().trim());
                        String newPhoneNumber = shelterPhoneNumberField.getText().trim();

                        if (!newPhoneNumber.matches("\\d{3}-\\d{3}-\\d{3}")) {
                            showAlert("Input Error", "Phone number must be in the format ddd-ddd-ddd.");
                            return null;
                        }

                        if (newName.isEmpty()) {
                            showAlert("Input Error", "Shelter name cannot be empty.");
                            return null;
                        }
                        if (newCapacity <= 0) {
                            showAlert("Input Error", "Max capacity must be a positive number.");
                            return null;
                        }

                        String oldName = selectedShelter.getShelterName();
                        if (!newName.equals(oldName)) {
                            shelterFacade.modifyShelter(oldName, newName);
                        }

                        selectedShelter.setShelterName(newName);
                        selectedShelter.setMaxCapacity(newCapacity);
                        selectedShelter.setPhoneNumber(newPhoneNumber);

                        loadShelters();
                    } catch (NumberFormatException e) {
                        showAlert("Input Error", "Please enter a valid number for max capacity.");
                    }
                }
                return null;
            });

            dialog.showAndWait();
        } else {
            showAlert("Selection Error", "Please select a shelter to edit.");
        }
    }

    private void createAnimalGrid(ComboBox<AnimalCondition> conditionComboBox) {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(animalNameField, 1, 0);
        grid.add(new Label("Species:"), 0, 1);
        grid.add(animalSpeciesField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(animalAgeField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(animalPriceField, 1, 3);
        grid.add(new Label("Condition:"), 0, 4);
        grid.add(conditionComboBox, 1, 4);
    }

    @FXML
    private void handleDelete() {
        Alert choiceDialog = new Alert(Alert.AlertType.CONFIRMATION);
        choiceDialog.setTitle("Delete Option");
        choiceDialog.setHeaderText("Choose an option");
        choiceDialog.setContentText("Would you like to delete an animal or a shelter?");

        ButtonType deleteAnimalButton = new ButtonType("Delete Animal");
        ButtonType deleteShelterButton = new ButtonType("Delete Shelter");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        choiceDialog.getButtonTypes().setAll(deleteAnimalButton, deleteShelterButton, cancelButton);

        choiceDialog.showAndWait().ifPresent(choice -> {
            if (choice == deleteAnimalButton) {
                handleDeleteAnimal();
            } else if (choice == deleteShelterButton) {
                handleDeleteShelter();
            }
        });
    }

    private void handleDeleteAnimal() {
        selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

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
                shelterFacade.removeAnimal(selectedShelter.getShelterName(), selectedAnimal);
                loadAnimals(selectedShelter);
            }
        } else {
            showAlert("Selection Error", "Please select an animal to delete.");
        }
    }

    private void handleDeleteShelter() {
        if (selectedShelter != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Are you sure you want to delete this shelter?");
            confirmationAlert.setContentText("Shelter: " + selectedShelter.getShelterName());

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    shelterFacade.removeShelter(selectedShelter.getShelterName());
                    loadShelters();
                }
            });
        } else {
            showAlert("Selection Error", "Please select a shelter to delete.");
        }
    }

    @FXML
    private void handleManageData() {
        /*Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Data");

        ButtonType exportBinary = new ButtonType("Export Binary");
        ButtonType importBinary = new ButtonType("Import Binary");
        ButtonType exportCSV = new ButtonType("Export CSV");
        ButtonType importCSV = new ButtonType("Import CSV");
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(
                exportBinary, importBinary, exportCSV, importCSV, close);

        dialog.setResultConverter(button -> {
            if (button == exportBinary) {
                shelterFacade.saveToFile("shelters.bin");
            } else if (button == importBinary) {
                shelterFacade.loadFromFile("shelters.bin");
                loadShelters();
            } else if (button == exportCSV && selectedShelter != null) {
                shelterFacade.exportToCSV(selectedShelter.getShelterName(),
                        selectedShelter.getShelterName() + ".csv");
            }
            return null;
        });

        dialog.showAndWait();*/
    }
}
