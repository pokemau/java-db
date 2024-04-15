package com.example.csit228f2_2;

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
    private void onUpdateUsernameClick() {
        System.out.println("UPDATE USERNAME");
    }
}