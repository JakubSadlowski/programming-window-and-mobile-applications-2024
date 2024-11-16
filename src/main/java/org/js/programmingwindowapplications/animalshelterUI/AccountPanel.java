package org.js.programmingwindowapplications.animalshelterUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.js.programmingwindowapplications.Main;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import java.util.Map;

public class AccountPanel {
    protected AnimalShelter selectedShelter;
    protected Animal selectedAnimal;
    protected ObservableList<Animal> animalData = FXCollections.observableArrayList();
    protected ObservableList<AnimalShelter> shelterData = FXCollections.observableArrayList();
    protected ShelterFacade shelterFacade;
    private Main mainApp;

    @FXML
    protected ListView<AnimalShelter> shelterListView;
    @FXML
    protected TableView<Animal> animalTable;
    @FXML
    private TableColumn<Animal, String> nameColumn;
    @FXML
    private TableColumn<Animal, String> speciesColumn;
    @FXML
    private TableColumn<Animal, Integer> ageColumn;
    @FXML
    private TableColumn<Animal, Double> priceColumn;
    @FXML
    private TableColumn<Animal, String> conditionColumn;

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
        shelterListView.getSelectionModel().selectedItemProperty().removeListener(this::handleShelterSelectionChange);
        shelterListView.getSelectionModel().selectedItemProperty().addListener(this::handleShelterSelectionChange);

        if (selectedShelter != null && shelterData.contains(selectedShelter)) {
            shelterListView.getSelectionModel().select(selectedShelter);
        }
    }

    private void handleShelterSelectionChange(ObservableValue<? extends AnimalShelter> observable, AnimalShelter oldValue, AnimalShelter newValue) {
        if (newValue != null) {
            loadAnimals(newValue);
        }
    }

    public void loadAnimals(AnimalShelter shelter) {
        selectedShelter = shelter;
        animalData.clear();
        animalData.addAll(shelterFacade.getAnimals(shelter.getShelterName()));
        animalTable.setItems(animalData);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        speciesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecies()));
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        conditionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCondition().toString()));
    }

    @FXML
    protected void handleLogout() {
        try {
            if (mainApp != null) {
                mainApp.showLoginView();
            } else {
                showAlert("Error", "Main application context not set.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Logout Error", "An error occurred while trying to log out. Please try again.");
        }
    }


    protected void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
