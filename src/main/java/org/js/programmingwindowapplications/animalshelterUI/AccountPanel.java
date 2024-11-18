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

import java.util.Comparator;
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
    @FXML
    private TextField filterTextField;
    @FXML
    private ComboBox<String> stateComboBox;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        stateComboBox.setItems(FXCollections.observableArrayList("All", "HEALTHY", "ADOPTED", "UNHEALTHY", "QUARANTINE"));
        stateComboBox.getSelectionModel().select("All");
        stateComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        filterTextField.setOnAction(event -> applyFilters());
    }

    private void applyFilters() {
        String filterText = filterTextField.getText().toLowerCase();
        String selectedState = stateComboBox.getSelectionModel().getSelectedItem();

        if (selectedShelter == null) {
            ObservableList<AnimalShelter> filteredShelters = FXCollections.observableArrayList();

            for (AnimalShelter shelter : shelterFacade.getShelters().values()) {
                if (filterText.isEmpty() || shelter.getShelterName().toLowerCase().contains(filterText)) {
                    filteredShelters.add(shelter);
                }
            }

            shelterListView.setItems(filteredShelters);

            if (filteredShelters.isEmpty()) {
                shelterListView.setPlaceholder(new Label("No shelters match the filter."));
            }

            return;
        }

        ObservableList<Animal> filteredAnimals = FXCollections.observableArrayList();

        for (Animal animal : shelterFacade.getAnimals(selectedShelter.getShelterName())) {
            boolean matchesFilter = filterText.isEmpty() || animal.getName().toLowerCase().contains(filterText);
            boolean matchesState = "All".equals(selectedState) || animal.getCondition().toString().equalsIgnoreCase(selectedState);

            if (matchesFilter && matchesState) {
                filteredAnimals.add(animal);
            }
        }

        animalTable.setItems(filteredAnimals);

        if (filteredAnimals.isEmpty()) {
            animalTable.setPlaceholder(new Label("No animals match the filter."));
        }
    }

    @FXML
    private void handleSortSheltersByName() {
        if (shelterData != null) {
            shelterData.sort(Comparator.comparing(AnimalShelter::getShelterName));
            shelterListView.setItems(shelterData);
        }
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
