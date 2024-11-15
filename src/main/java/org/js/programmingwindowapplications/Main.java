package org.js.programmingwindowapplications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.js.programmingwindowapplications.animalshelter.Animal;
import org.js.programmingwindowapplications.animalshelter.AnimalCondition;
import org.js.programmingwindowapplications.animalshelter.AnimalShelter;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;
import org.js.programmingwindowapplications.animalshelterUI.*;

public class Main extends Application {
    private Stage primaryStage;
    private ShelterFacade shelterFacade;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        AccountsManager accountsManager = addAccounts();
        ShelterManager shelterManager = addShelters();
        this.shelterFacade = new ShelterFacade(shelterManager, accountsManager);
        showLoginView();
    }

    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-panel.fxml"));
        Scene scene = new Scene(loader.load());

        LoginPanel controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Shelter Login");
        primaryStage.show();
    }

    public void showAdminView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-panel.fxml"));
        Scene scene = new Scene(loader.load());

        AdminPanel controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setMainApp(this);
        controller.loadShelters();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Panel - Manage Animals");
    }

    public void showClientView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client-panel.fxml"));
        Scene scene = new Scene(loader.load());

        ClientPanel controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setMainApp(this);
        controller.loadShelters();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Client Panel - Browse Animals");
    }

    private AccountsManager addAccounts() {
        AccountsManager accountsManager = new AccountsManager();

        accountsManager.addAccount(new AdminAccount());
        accountsManager.addAccount(new ClientAccount("Jakub", "Sadlowski", "sado", "sado"));

        return accountsManager;
    }

    private ShelterManager addShelters() {
        ShelterManager manager = new ShelterManager();

        manager.addShelter("Wild Paws", 6);
        manager.addShelter("Green Meadows", 4);
        manager.addShelter("Happy Tails Rescue", 5);
        manager.addShelter("Forest Friends", 7);
        manager.addShelter("Sunset Haven", 3);
        manager.addShelter("Ocean Breeze Shelter", 8);

        Animal animal1 = new Animal("Rocky", "Dog", AnimalCondition.HEALTHY, 3, 150.0);
        Animal animal2 = new Animal("Luna", "Cat", AnimalCondition.UNHEALTHY, 2, 80.0);
        Animal animal3 = new Animal("Charlie", "Rabbit", AnimalCondition.ADOPTED, 1, 60.0);
        Animal animal4 = new Animal("Bella", "Dog", AnimalCondition.HEALTHY, 4, 120.0);
        Animal animal5 = new Animal("Max", "Cat", AnimalCondition.QUARANTINE, 2, 70.0);
        Animal animal6 = new Animal("Milo", "Rabbit", AnimalCondition.UNHEALTHY, 3, 50.0);
        Animal animal7 = new Animal("Zoe", "Dog", AnimalCondition.HEALTHY, 5, 180.0);
        Animal animal8 = new Animal("Oliver", "Cat", AnimalCondition.HEALTHY, 3, 100.0);
        Animal animal9 = new Animal("Ruby", "Rabbit", AnimalCondition.QUARANTINE, 2, 65.0);
        Animal animal10 = new Animal("Socks", "Dog", AnimalCondition.UNHEALTHY, 6, 110.0);
        Animal animal11 = new Animal("Misty", "Cat", AnimalCondition.ADOPTED, 1, 85.0);
        Animal animal12 = new Animal("Buddy", "Rabbit", AnimalCondition.HEALTHY, 4, 55.0);
        Animal animal13 = new Animal("Shadow", "Dog", AnimalCondition.UNHEALTHY, 3, 95.0);
        Animal animal14 = new Animal("Toby", "Cat", AnimalCondition.HEALTHY, 2, 75.0);
        Animal animal15 = new Animal("Oscar", "Rabbit", AnimalCondition.QUARANTINE, 2, 50.0);
        Animal animal16 = new Animal("Lily", "Dog", AnimalCondition.HEALTHY, 4, 140.0);
        Animal animal17 = new Animal("Daisy", "Cat", AnimalCondition.UNHEALTHY, 3, 60.0);
        Animal animal18 = new Animal("Leo", "Rabbit", AnimalCondition.ADOPTED, 1, 45.0);
        Animal animal19 = new Animal("Coco", "Dog", AnimalCondition.HEALTHY, 4, 130.0);
        Animal animal20 = new Animal("Chloe", "Cat", AnimalCondition.QUARANTINE, 5, 90.0);

        AnimalShelter shelter1 = manager.getShelters().get("Wild Paws");
        AnimalShelter shelter2 = manager.getShelters().get("Green Meadows");
        AnimalShelter shelter3 = manager.getShelters().get("Happy Tails Rescue");
        AnimalShelter shelter4 = manager.getShelters().get("Forest Friends");
        AnimalShelter shelter5 = manager.getShelters().get("Sunset Haven");
        AnimalShelter shelter6 = manager.getShelters().get("Ocean Breeze Shelter");

        shelter1.addAnimal(animal1);
        shelter1.addAnimal(animal2);
        shelter1.addAnimal(animal3);

        shelter2.addAnimal(animal4);
        shelter2.addAnimal(animal5);
        shelter2.addAnimal(animal6);

        shelter3.addAnimal(animal7);
        shelter3.addAnimal(animal8);
        shelter3.addAnimal(animal9);

        shelter4.addAnimal(animal10);
        shelter4.addAnimal(animal11);
        shelter4.addAnimal(animal12);

        shelter5.addAnimal(animal13);
        shelter5.addAnimal(animal14);
        shelter5.addAnimal(animal15);

        shelter6.addAnimal(animal16);
        shelter6.addAnimal(animal17);
        shelter6.addAnimal(animal18);
        shelter6.addAnimal(animal19);
        shelter6.addAnimal(animal20);

        return manager;
    }
}
