package com.example.roomdatabasetutorial.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.roomdatabasetutorial.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();

    private static volatile NotesDatabase notesDatabaseInstance;

    public static NotesDatabase getDatabase(final Context context) {
        if (notesDatabaseInstance == null) {
            synchronized (NotesDatabase.class) {
                if (notesDatabaseInstance == null) {
                    notesDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return notesDatabaseInstance;
    }
}
