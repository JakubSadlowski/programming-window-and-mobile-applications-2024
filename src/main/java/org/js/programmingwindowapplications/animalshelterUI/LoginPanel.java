package org.js.programmingwindowapplications.animalshelterUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.js.programmingwindowapplications.Main;

public class LoginPanel {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private ShelterFacade shelterFacade;
    private Stage primaryStage;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String role = shelterFacade.login(username, password);
        try {
            if ("admin".equals(role)) {
                showAdminView();
            } else if ("user".equals(role)) {
                showClientView();
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred.");
        }
    }

    private void showAdminView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-panel.fxml"));
        Scene adminScene = new Scene(loader.load());

        AdminPanel adminController = loader.getController();
        adminController.setShelterFacade(shelterFacade);
        adminController.setMainApp(Main.getInstance());
        adminController.loadShelters();

        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Panel - Manage Animals");
    }

    private void showClientView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client-panel.fxml"));
        Scene clientScene = new Scene(loader.load());

        ClientPanel clientController = loader.getController();
        clientController.setShelterFacade(shelterFacade);
        clientController.setMainApp(Main.getInstance());
        clientController.loadShelters();

        primaryStage.setScene(clientScene);
        primaryStage.setTitle("Client Panel - Browse Animals");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

