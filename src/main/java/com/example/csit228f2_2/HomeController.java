package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
    private void onEditNoteClick(int noteID) {
        System.out.println("EDIT NOTE ID: " + noteID);
    }

    @FXML
    private void onDeleteNoteClick(int noteID) {
        System.out.println("DELETE NOTE ID: " + noteID);
    }

    @FXML
    void logout(ActionEvent event) {
        System.out.println(event.toString());
    }

    private void listNotes() {

        for (Note n : notes) {
            HBox noteCont = new HBox();
            noteCont.getStyleClass().add("note-cont");


            Text content = new Text(n.content);
            content.setFont(new Font("Arial", 19));
            content.setWrappingWidth(563);

            VBox buttonsCont = new VBox();
            Button editBtn = new Button("Edit");
            editBtn.setPrefWidth(100);
            editBtn.setPrefHeight(25);
            editBtn.setStyle("-fx-cursor: hand;");
            editBtn.setUserData(n.ID);
            editBtn.setOnAction(event -> onEditNoteClick((int) editBtn.getUserData()));
            Button deleteBtn = new Button("Delete");
            deleteBtn.setPrefWidth(100);
            deleteBtn.setPrefHeight(25);
            deleteBtn.setStyle("-fx-cursor: hand;");
            deleteBtn.setUserData(n.ID);
            deleteBtn.setOnAction(event -> onDeleteNoteClick((int) deleteBtn.getUserData()));

            noteCont.getChildren().add(content);
            buttonsCont.getChildren().add(editBtn);
            buttonsCont.getChildren().add(deleteBtn);
            noteCont.getChildren().add(buttonsCont);

            noteCont.setId(Integer.toString(n.ID));

//            mainAnchorPane.getChildren().add(noteCont);
            notesContainer.getChildren().add(noteCont);
        }
    }
}
