package com.example.csit228f2_2;

public class Note {
    int ID;
    int userID;
    String content;
    String title;

    public Note(int ID) {
        this.ID = ID;
    }

    public Note(int ID, int userID, String title, String content) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.content = content;
    }
}
