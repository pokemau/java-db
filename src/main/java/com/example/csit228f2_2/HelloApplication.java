package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class HelloApplication extends Application {

    public static Scene mainScene;
    public static Stage mainStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        SQLConnection.InitTables();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 700, 400);

        stage.setScene(scene);
        stage.setTitle("My App");
        stage.setResizable(false);
        stage.show();
    }
}