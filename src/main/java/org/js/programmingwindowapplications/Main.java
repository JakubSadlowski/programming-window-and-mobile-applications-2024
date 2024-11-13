package org.js.programmingwindowapplications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.js.programmingwindowapplications.animalshelterUI.AccountsManager;
import org.js.programmingwindowapplications.animalshelterUI.LoginController;
import org.js.programmingwindowapplications.animalshelterUI.ShelterFacade;

public class Main extends Application {
    private Stage primaryStage;
    private ShelterFacade shelterFacade;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        AccountsManager accountsManager = new AccountsManager();
        this.shelterFacade = new ShelterFacade(accountsManager);
        showLoginView();
    }

    private void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-panel.fxml"));
        Scene scene = new Scene(loader.load());

        LoginController controller = loader.getController();
        controller.setShelterFacade(shelterFacade);
        controller.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Animal Shelter Login");
        primaryStage.show();
    }

    public void showAdminView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-panel.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Panel - Manage Animals");
    }

    public void showClientView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client-panel.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client Panel - Browse Animals");
    }
}
