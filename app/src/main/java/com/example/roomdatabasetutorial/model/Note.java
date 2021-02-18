package com.example.roomdatabasetutorial.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String mNoteText;

    public Note(@NonNull String id, @NonNull String mNoteText) {
        this.id = id;
        this.mNoteText = mNoteText;
    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNoteText() {
        return mNoteText;
    }

    public void setNoteText(@NonNull String mNoteText) {
        this.mNoteText = mNoteText;
    }
}
