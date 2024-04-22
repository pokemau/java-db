package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Text notifText;
    @FXML
    Button loginBtn;
    @FXML
    Button registerBtn;

    @FXML
    private void onLoginAccountClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            notifText.setText("Username and password are required");
            return;
        }

        if (SQLConnection.getUser(username, password)) {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            usernameField.setText("");
            passwordField.setText("");
            notifText.setText("");
            stage.setScene(scene);
            stage.show();


        } else {
            notifText.setText("User does not exist!");
        }

    }

    @FXML
    private void onRegisterAccountClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            notifText.setText("Username and password are required!");
            return;
        }
        if (SQLConnection.checkIfUsernameIsUnique(username)) {
            SQLConnection.createUser(username, password);

            notifText.setText("Created account!");
        } else {
            notifText.setText("Username already exists!");
        }
    }
}
