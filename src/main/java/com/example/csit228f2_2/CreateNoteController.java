package com.example.csit228f2_2;

import db.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class CreateNoteController {
    @FXML
    TextField titleField;
    @FXML
    TextArea contentField;

    @FXML
    public void initialize() {
        if (SQLConnection.currentNote.ID != -1) {
            populateFields();
        }
    }

    @FXML
    private void onSaveClick(ActionEvent event) {
        if (titleField.getText().isEmpty() || contentField.getText().isEmpty()) {
            return;
        }

        String title = titleField.getText();
        String content = contentField.getText();

        if (SQLConnection.currentNote.ID == -1) {
            int res = SQLConnection.createNote(titleField.getText(), contentField.getText());
            if (res != -1) {
                SQLConnection.currentNote.setEmpty();
                loadHome(event);
            }
        } else {
            SQLConnection.updateNote(title, content, SQLConnection.currentNote.ID);
            SQLConnection.currentNote.setEmpty();
            loadHome(event);
        }
    }

    private void loadHome(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void populateFields() {
        titleField.setText(SQLConnection.currentNote.title);
        contentField.setText(SQLConnection.currentNote.content);
    }
}
