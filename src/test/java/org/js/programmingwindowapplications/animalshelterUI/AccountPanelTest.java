package org.js.programmingwindowapplications.animalshelterUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.js.programmingwindowapplications.Main;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelterUI.ShelterFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountPanelTest {

    private AccountPanel accountPanel;
    private ShelterFacade mockShelterFacade;
    private Main mockMainApp;
    private ListView<AnimalShelter> mockShelterListView;
    private TableView<Animal> mockAnimalTable;
    private AnimalShelter mockShelter;

    @BeforeEach
    void setUp() {
        mockShelterFacade = mock(ShelterFacade.class);
        mockMainApp = mock(Main.class);
        accountPanel = new AccountPanel();
        accountPanel.setShelterFacade(mockShelterFacade);
        accountPanel.setMainApp(mockMainApp);

        mockShelterListView = mock(ListView.class);
        mockAnimalTable = mock(TableView.class);
        accountPanel.shelterListView = mockShelterListView;
        accountPanel.animalTable = mockAnimalTable;

        // Create a mock shelter
        mockShelter = new AnimalShelter("Shelter1", 10, "555-111-222");
    }

    @Test
    void testLoadShelters() {
        // Given
        ObservableList<AnimalShelter> shelterData = FXCollections.observableArrayList(mockShelter);
        when(mockShelterFacade.getShelters()).thenReturn(Map.of("Shelter1", mockShelter));

        // When
        accountPanel.loadShelters();

        // Then
        assertEquals(shelterData, accountPanel.shelterData, "Shelters should be loaded correctly.");
        verify(mockShelterListView).setItems(accountPanel.shelterData);
    }

    @Test
    void testLoadAnimals() {
        // Given
        Animal animal = new Animal("Tommy", "Dog", AnimalCondition.HEALTHY, 3, 50.0);
        ObservableList<Animal> animalData = FXCollections.observableArrayList(animal);
        when(mockShelterFacade.getAnimals(mockShelter.getShelterName())).thenReturn(animalData);

        // When
        accountPanel.loadAnimals(mockShelter);

        // Then
        assertEquals(animalData, accountPanel.animalData, "Animals should be loaded correctly.");
        verify(mockAnimalTable).setItems(accountPanel.animalData);
    }

    /*@Test
    void testHandleLogout() {
        // Given
        doNothing().when(mockMainApp).showLoginView();

        // When
        accountPanel.handleLogout();

        // Then
        verify(mockMainApp).showLoginView();
    }*/
}
