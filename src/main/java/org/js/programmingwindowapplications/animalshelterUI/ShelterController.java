package org.js.programmingwindowapplications.animalshelterUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.js.programmingwindowapplications.Main;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelterUI.ShelterFacade;

import java.util.Map;

public class ShelterController {

    protected AnimalShelter selectedShelter;
    protected ObservableList<Animal> animalData = FXCollections.observableArrayList();
    protected ObservableList<AnimalShelter> shelterData = FXCollections.observableArrayList();
    protected ShelterFacade shelterFacade;
    private Main mainApp;

    @FXML
    protected ListView<AnimalShelter> shelterListView;
    @FXML
    protected TableView<Animal> animalTable;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void loadShelters() {
        shelterData.clear();
        Map<String, AnimalShelter> sheltersMap = shelterFacade.getShelters();
        shelterData.addAll(sheltersMap.values());
        shelterListView.setItems(shelterData);

        shelterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAnimals(newValue);
            }
        });
    }

    public void loadAnimals(AnimalShelter shelter) {
        selectedShelter = shelter;
        animalData.clear();
        animalData.addAll(shelter.getAnimalList());
        animalTable.setItems(animalData);
    }

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
