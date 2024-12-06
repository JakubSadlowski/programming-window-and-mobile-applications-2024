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
import org.js.programmingwindowapplications.db.HibernateUtil;

public class Main extends Application {
    private Stage primaryStage;
    private ShelterFacade shelterFacade;
    private static Main instance;

    public static void main(String[] args) {
        HibernateUtil.testConnection();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        DataGenerator dataGenerator = DataGenerator.getInstance();
        AccountsManager accountsManager = dataGenerator.addAccounts();
        ShelterManager shelterManager = dataGenerator.addShelters();
        this.shelterFacade = new ShelterFacade(shelterManager, accountsManager);
        showLoginView();
    }

    public static Main getInstance() {
        return instance;
    }

    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/js/programmingwindowapplications/animalshelterUI/login-panel.fxml"));
        Scene scene = new Scene(loader.load());

        LoginPanel controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Shelter Login");
        primaryStage.show();
    }
}
