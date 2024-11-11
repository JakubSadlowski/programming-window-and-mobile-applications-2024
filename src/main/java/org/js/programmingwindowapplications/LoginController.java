package org.js.programmingwindowapplications;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    private ShelterFacade shelterFacade;
    private Main mainApp;

    public void setShelterFacade(ShelterFacade shelterFacade) {
        this.shelterFacade = shelterFacade;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

   /* public String validateUser(String username, String password) {
        // Simple role assignment for demonstration
        if ("admin".equals(username) && "adminpass".equals(password)) {
            return "admin";
        } else if ("user".equals(username) && "userpass".equals(password)) {
            return "user";
        }
        return null;
    }*/

    public void showError(String message) {
        // Show error message in the login view
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
                errorLabel.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred. Please try again.");
        }
    }
}


