package com.example.roomdatabasetutorial.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabasetutorial.model.Note;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    void inset(Note note);

    @Query("SELECT * FROM Notes")
    LiveData<List<Note>> getAllNotes();

}
