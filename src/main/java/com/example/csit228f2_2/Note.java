package com.example.csit228f2_2;

public class Note {
    int ID;
    int userID;
    String content;
    String title;

    public Note() {
        this.ID = -1;
        this.userID = -1;
    }

    public Note(int ID, int userID, String title, String content) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.content = content;
    }

    public void setEmpty() {
        ID = -1;
        userID = -1;
        content = "";
        title = "";
    }
}
