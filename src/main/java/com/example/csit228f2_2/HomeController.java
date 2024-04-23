package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeController {

    ArrayList<Note> notes;

    @FXML
    Button logoutBtn;
    @FXML
    VBox notesContainer;
    @FXML
    AnchorPane mainAnchorPane;

    @FXML
    public void initialize() {
        notes = SQLConnection.getNotesFromDB();

        listNotes();
    }

    @FXML
    private void onCreateNoteClick(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-note.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onEditNoteClick(ActionEvent event, int noteID) {
        Note curr = null;
        for (Note n : notes) {
            if (n.ID == noteID) {
                SQLConnection.currentNote = n;
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-note.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }


        System.out.println("EDIT NOTE ID: " + noteID);
    }

    @FXML
    private void onDeleteNoteClick(ActionEvent event, int noteID) {
        SQLConnection.deleteNote(noteID);

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        System.out.println(event.toString());
    }

    private void listNotes() {

        for (Note n : notes) {
            HBox noteCont = new HBox();
            noteCont.getStyleClass().add("note-cont");

            Text title = new Text(n.title);
            title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            title.setWrappingWidth(563);
            Text content = new Text(n.content);
            content.setFont(new Font("Arial", 14));
            content.setWrappingWidth(563);

            VBox textCont = new VBox();
            textCont.getChildren().add(title);
            textCont.getChildren().add(content);

            VBox buttonsCont = new VBox();
            Button editBtn = new Button("Edit");
            editBtn.setPrefWidth(100);
            editBtn.setPrefHeight(25);
            editBtn.setStyle("-fx-cursor: hand;");
            editBtn.setUserData(n.ID);
            editBtn.setOnAction(event -> onEditNoteClick(event, (int) editBtn.getUserData()));
            Button deleteBtn = new Button("Delete");
            deleteBtn.setPrefWidth(100);
            deleteBtn.setPrefHeight(25);
            deleteBtn.setStyle("-fx-cursor: hand;");
            deleteBtn.setUserData(n.ID);
            deleteBtn.setOnAction(event -> onDeleteNoteClick(event, (int) deleteBtn.getUserData()));

            noteCont.getChildren().add(textCont);
            buttonsCont.getChildren().add(editBtn);
            buttonsCont.getChildren().add(deleteBtn);
            noteCont.getChildren().add(buttonsCont);

            noteCont.setId(Integer.toString(n.ID));

            notesContainer.getChildren().add(noteCont);
        }
    }
}
