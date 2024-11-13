package org.js.programmingwindowapplications.animalshelterUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.js.programmingwindowapplications.Main;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;

import java.util.Map;

public class AdminController {
    private AnimalShelter selectedShelter;
    private ObservableList<Animal> animalData = FXCollections.observableArrayList();
    private ObservableList<AnimalShelter> shelterData = FXCollections.observableArrayList();
    private ShelterFacade shelterFacade;
    private Main mainApp;

    @FXML
    private ListView<AnimalShelter> shelterListView; // ListView for shelters
    @FXML
    private TableView<Animal> animalTable; // TableView for animals
    @FXML
    private TableColumn<Animal, Integer> idColumn;
    @FXML
    private TableColumn<Animal, String> nameColumn;
    @FXML
    private TableColumn<Animal, String> typeColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    // Load shelters into the ListView
    public void loadShelters() {
        shelterData.clear();
        Map<String, AnimalShelter> sheltersMap = shelterFacade.getShelters(); // Get the map of shelters

        // Add the shelters (values from the map) to the observable list
        shelterData.addAll(sheltersMap.values());

        shelterListView.setItems(shelterData);

        shelterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAnimals(newValue); // Load animals for the selected shelter
            }
        });
    }

    // Load animals for the selected shelter
    public void loadAnimals(AnimalShelter shelter) {
        selectedShelter = shelter;
        animalData.clear();
        animalData.addAll(shelter.getAnimalList()); // Assuming getAnimals() returns a list of animals
        animalTable.setItems(animalData);
    }

    // Handle the event to add a new animal
    public void handleAddAnimal(ActionEvent actionEvent) {
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

    // Handle the event to edit an existing animal
    public void handleEditAnimal(ActionEvent actionEvent) {
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

    // Handle the event to delete an animal
    public void handleDeleteAnimal(ActionEvent actionEvent) {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            selectedShelter.removeAnimal(selectedAnimal);
            loadAnimals(selectedShelter);
        } else {
            showAlert("Selection Error", "Please select an animal to delete.");
        }
    }

    // Show alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
