package org.js.programmingwindowapplications.animalshelterUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.js.programmingwindowapplications.Main;

public class LoginPanel {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private ShelterFacade shelterFacade;
    private Main mainApp;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String role = shelterFacade.validateUser(username, password);
        try {
            if ("admin".equals(role)) {
                mainApp.showAdminView();
            } else if ("user".equals(role)) {
                mainApp.showClientView();
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
