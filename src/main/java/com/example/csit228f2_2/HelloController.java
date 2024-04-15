package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;

    @FXML
    private Button btnUpdateUsername;
    @FXML
    private Button btnUpdatePassword;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnDeleteAccount;

    @FXML
    private void onUpdateUsernameClick() {
        String username = newUsername.getText();
        if (username.isEmpty()) { return; }

        if (SQLConnection.updateUsername(username)) {
            newUsername.setText("");
            // show success message
        }
    }

    @FXML
    private void onUpdatePasswordClick() {
        String password = newPassword.getText();
        if (password.isEmpty()) { return; }

        if (SQLConnection.updateUserPassword(password)) {
            newPassword.setText("");
            // show success message
        }

    }

    @FXML
    private void onDeleteAccountClick() {
        if (SQLConnection.deleteUserAccount()) {
            onLogoutClick();
        }
    }


    @FXML
    private void onLogoutClick() {
        SQLConnection.CURRENT_USER_ID = -1;
        HelloApplication.mainStage.setScene(HelloApplication.mainScene);
        HelloApplication.mainStage.show();
    }
}