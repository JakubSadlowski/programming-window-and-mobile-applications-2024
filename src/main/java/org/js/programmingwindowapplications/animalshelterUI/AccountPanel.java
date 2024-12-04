package org.js.programmingwindowapplications.animalshelterUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
    private void initialize() {
        stateComboBox.setItems(FXCollections.observableArrayList("All", "HEALTHY", "ADOPTED", "UNHEALTHY", "QUARANTINE"));
        stateComboBox.getSelectionModel().select("All");
        stateComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        filterTextField.setOnAction(event -> applyFilters());

        // Configure the shelter list cell factory
        shelterListView.setCellFactory(listView -> new ListCell<AnimalShelter>() {
            private final HBox container = new HBox(10);
            private final VBox shelterInfo = new VBox(5);
            private final Label nameLabel = new Label();
            private final HBox ratingContainer = new HBox(2);
            private final Label ratingCountLabel = new Label();

            // SVG definitions for stars
            private static final String FULL_STAR_SVG =
                    "M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z";
            private static final String EMPTY_STAR_SVG =
                    "M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2ZM12 4.95L9.84 9.16L5.08 9.85L8.54 13.2L7.71 17.94L12 15.79L16.29 17.94L15.46 13.2L18.92 9.85L14.16 9.16L12 4.95Z";

            {
                container.getChildren().add(shelterInfo);
                shelterInfo.getChildren().addAll(nameLabel, ratingContainer, ratingCountLabel);
                container.setAlignment(Pos.CENTER_LEFT);
                ratingContainer.setAlignment(Pos.CENTER_LEFT);
            }

            private SVGPath createStar(boolean filled) {
                SVGPath star = new SVGPath();
                star.setContent(filled ? FULL_STAR_SVG : EMPTY_STAR_SVG);
                star.setFill(Color.GOLD);
                star.setScaleX(0.8);
                star.setScaleY(0.8);
                return star;
            }

            @Override
            protected void updateItem(AnimalShelter shelter, boolean empty) {
                super.updateItem(shelter, empty);

                if (empty || shelter == null) {
                    setGraphic(null);
                } else {
                    nameLabel.setText(shelter.getShelterName());
                    nameLabel.setStyle("-fx-font-weight: bold");

                    // Get rating information from the facade
                    Map<String, Object> shelterInfo = shelterFacade.getShelterWithRatings(shelter.getShelterName());
                    double avgRating = Math.round((double) shelterInfo.get("averageRating") * 2) / 2.0; // Round to nearest 0.5
                    long ratingCount = (long) shelterInfo.get("ratingCount");

                    // Clear previous stars
                    ratingContainer.getChildren().clear();

                    // Add full and empty stars
                    int fullStars = (int) avgRating;
                    boolean hasHalfStar = avgRating % 1 != 0;

                    // Add full stars
                    for (int i = 0; i < fullStars; i++) {
                        ratingContainer.getChildren().add(createStar(true));
                    }

                    // Add empty stars
                    for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) {
                        ratingContainer.getChildren().add(createStar(false));
                    }

                    ratingCountLabel.setText(String.format(" • %d ratings", ratingCount));
                    setGraphic(container);
                }
            }
        });
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
