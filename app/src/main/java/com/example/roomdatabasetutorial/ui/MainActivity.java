package com.example.roomdatabasetutorial.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roomdatabasetutorial.R;
import com.example.roomdatabasetutorial.adapters.NotesListAdapter;
import com.example.roomdatabasetutorial.databases.NotesDao;
import com.example.roomdatabasetutorial.databases.NotesDatabase;
import com.example.roomdatabasetutorial.model.Note;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText editTextNote;
    Button buttonAddNote;
    RecyclerView notesRecyclerView;

    NotesDatabase notesDatabase;
    NotesDao notesDao;

    private LiveData<List<Note>> allNotes;
    NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);

        notesRecyclerView = findViewById(R.id.notes_recyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesListAdapter = new NotesListAdapter();
        notesRecyclerView.setAdapter(notesListAdapter);

        notesDatabase = NotesDatabase.getDatabase(this);
        notesDao = notesDatabase.notesDao();

        allNotes = notesDao.getAllNotes();
        allNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d(TAG, "onChanged: observer called");
                notesListAdapter.setNotes(notes);
            }
        });

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(UUID.randomUUID().toString(),editTextNote.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        notesDao.inset(note);
                    }
                }).start();

            }
        });
    }
}