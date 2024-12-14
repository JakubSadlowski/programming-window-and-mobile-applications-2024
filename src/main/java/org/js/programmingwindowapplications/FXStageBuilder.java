package org.js.programmingwindowapplications;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.js.programmingwindowapplications.animalshelter.ShelterManager;
import org.js.programmingwindowapplications.animalshelterUI.AccountsManager;
import org.js.programmingwindowapplications.animalshelterUI.LoginPanel;
import org.js.programmingwindowapplications.animalshelterUI.ShelterFacade;

import java.io.IOException;

public class FXStageBuilder {

    public static void buildStage(FXMLLoader loader, Stage primaryStage) throws IOException {
        DataGenerator dataGenerator = DataGenerator.getInstance();
        AccountsManager accountsManager = dataGenerator.addAccounts();
        ShelterManager shelterManager = dataGenerator.addShelters();
        ShelterFacade shelterFacade = new ShelterFacade(shelterManager, accountsManager);
        showLoginView(loader, primaryStage, shelterFacade);
    }

    public static void showLoginView(FXMLLoader loader, Stage primaryStage, ShelterFacade shelterFacade) throws IOException {
        LoginPanel controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Shelter Login");
        primaryStage.show();
    }
}
